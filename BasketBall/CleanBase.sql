-- Clean Base version for the basket ball database

-- Table Section

	-- For the Team
	create table Equipe(
		idEquipe varchar(50) primary key,
		nomEquipe varchar(250)
	);

	-- For the players
	create table Joueur(
		idJoueur varchar(50) primary key,
		nomJoueur varchar(250),
		idEquipe varchar(50),
		foreign key (idEquipe) references Equipe(idEquipe)
	);

	-- For the Match
	create table Match(
		idMatch varchar(50) primary key,
		idEquipe1 varchar(50),
		idEquipe2 varchar(50),
		foreign key (idEquipe1) references Equipe(idEquipe),
		foreign key (idEquipe2) references Equipe(idEquipe)
	);

	-- For the type of Action
	create table act(
		idAct varchar(50) not null primary key,
		actName varchar(100)
	);

	-- For the Action table
	create table Action(
		idAction varchar(50) not null primary key,
		idJoueur varchar(50),
		idAct varchar(50),
		points integer,
		idMatch varchar(50),
		foreign key (idJoueur) references Joueur(idJoueur),
		foreign key (idMatch) references Match(idMatch),
		foreign key (idAct) references act(idAct)
	);

-- Sequence Section

	-- For the action table
	create sequence actionSequence
	increment by 1
	start with 1
	minvalue 1;

	-- For the match table
	create sequence matchSequence
		increment by 10
		start with 10
		minvalue 10;


-- View Section
	
	-- To get all the shoots order by the id Action they have descendantly
	create or replace view shoots ( idJoueur , idEquipe , points , idMatch)
		as 
		Select joueur.idJoueur , joueur.idEquipe , tir.points , tir.idMatch
		from joueur 
		join 
		(select idJoueur,points,idMatch,idAction from action where idAct = 'ACT0004') as tir 
		on tir.idJoueur = Joueur.idJoueur order by tir.idAction desc;

	-- Get the score per equipe 
	create or replace view ScorePerEquipe
		as
		Select idEquipe , sum(points) as points , idMatch
		from shoots
		group by idEquipe , idMatch;


	-- initialize all scores to 0 (zero)
	create view default_score
		as
		Select * from ScorePerEquipe
		UNION
		Select idEquipe1 , 0 as sum, idMatch
		FROM
		(
			select idMatch,idEquipe1 from match where (idMatch,idEquipe1) not in (Select idMatch,idEquipe from ScorePerEquipe) UNION select idMatch,idEquipe2 from match where (idMatch,idEquipe2) not in (
		Select idMatch,idEquipe from ScorePerEquipe)		
		) as un;

	create view listMatch (idMatch , equipe1 , equipe2)	
		as
		Select m.idMatch , e.nomEquipe , e1.nomEquipe
		from match as m
		join Equipe as e 
		on m.idEquipe1 = e.idEquipe
		join Equipe as e1 
		on m.idEquipe2 = e1.idEquipe;


-- Function section
	
	-- Get the next Value of the action sequence
	create function getIdAction()
		returns int
		language plpgsql
		as 
		$$
			BEGIN
				return nextVal('actionSequence');
			END;
		$$;

	-- Get the next value of the match sequence
	create function getIdMatch()
		returns int
		language plpgsql
		as 
		$$
			BEGIN
				return nextVal('matchSequence');
			END;
		$$;

	-- Get the statitics of a team by giving the match Id and the Id of the action
	create or replace function getScoreByAction( idm varchar(50) , ida varchar(50) )
		returns table (nomJoueur varchar(250),scored numeric , attempt bigint)
		language plpgsql
		as
		$$
		BEGIN
			RETURN QUERY
			Select e.nomJoueur,sum(
				CASE
					WHEN points is null then 0
					else points
				END
			) as made,
			CASE
				WHEN totals is null then 0
				else totals
			END
			
			from(
				Select  p.idJoueur , p.idMatch , sum(
					CASE
						WHEN points > 0 then 1
						else 0
					END
				)  as points , count(*) as totals
				from action as p
				where p.idMatch = idm and p.idAct = ida  group by p.idJoueur , p.idMatch
			) as s
				RIGHT OUTER JOIN (Select * from joueur where idEquipe in (Select idEquipe1 from match where idMatch = idm UNION Select idEquipe2 from match where idMatch = idm)
			) as e on s.idJoueur = e.idJoueur group by e.nomJoueur,totals,e.idEquipe order by e.idEquipe;
		END;
		$$;

	
-- insertion section / initial data
	insert into Action values
		('ACN0001','JOU0001','ACT0001',1,'MTH0001'),
		('ACN0002','JOU0003','ACT0004',2,'MTH0001'),
		('ACN0003','JOU0006','ACT0004',0,'MTH0001');
		

	insert into act values
		( 'ACT0001' , 'Passe Decisive' );
	insert into act values
		( 'ACT0002' , 'Rebonds Defensive' );
	insert into act values
		( 'ACT0003' , 'Rebonds Offensive' );
	insert into act values
		( 'ACT0004' , 'Tir' );

	insert into Equipe values
		( 'EQP0001' , 'Los Angeles Lakers' );
	insert into Equipe values
		( 'EQP0002' , 'Boston Celtics' );
	insert into Equipe values
		( 'EQP0003' , 'Miami Heats' );
	insert into Equipe values
		( 'EQP0004' , 'Chicago Bulls' );
	insert into Equipe values
		('EQP0005' , 'E1');
	insert into Equipe values
		('EQP0006' , 'EA');

	-- Los angeles lakers 5 players
	insert into Joueur values
		( 'JOU0001' , 'Patrick Beverley' , 'EQP0001' );
	insert into Joueur values
		( 'JOU0002' , 'Troy Brown Jr.' , 'EQP0001' );
	insert into Joueur values
		( 'JOU0003' , 'Thomas Bryant' , 'EQP0001' );
	insert into Joueur values
		( 'JOU0004' , 'Max Christie' , 'EQP0001' );
	insert into Joueur values
		( 'JOU0005' , 'Anthony Davis' , 'EQP0001' );

	-- Boston Celtics
	insert into Joueur values
		( 'JOU0006' , 'Malcolm Brogdon' , 'EQP0002' );
	insert into Joueur values
		( 'JOU0007' , 'Jaylen Brown' 	, 'EQP0002' );
	insert into Joueur values
		( 'JOU0008' , 'JD Davison' 		, 'EQP0002' );
	insert into Joueur values
		( 'JOU0009' , 'Danilo Gallinari', 'EQP0002' );
	insert into Joueur values
		( 'JOU0010' , 'Blake Griffin' 	, 'EQP0002' );


	-- Miami Heats
	insert into Joueur values
		( 'JOU0011' , 'Bam Adebayo' 	, 'EQP0003' );
	insert into Joueur values
		( 'JOU0012' , 'Jimmy Butler' 	, 'EQP0003' );
	insert into Joueur values
		( 'JOU0013' , 'Jamal Cain' 		, 'EQP0003' );
	insert into Joueur values
		( 'JOU0014' , 'Dewayne Dedmon'	, 'EQP0003' );
	insert into Joueur values
		( 'JOU0015' , 'Udonis Haslem' 	, 'EQP0003' );

	-- Chicago Bulls
	insert into Joueur values
		( 'JOU0016' , 'Lonzo Ball' 		, 'EQP0004' );
	insert into Joueur values
		( 'JOU0017' , 'Tony Bradley' 	, 'EQP0004' );
	insert into Joueur values
		( 'JOU0018' , 'Alex Caruso'		, 'EQP0004' );
	insert into Joueur values
		( 'JOU0019' , 'DeMar DeRozan'	, 'EQP0004' );
	insert into Joueur values
		( 'JOU0020' , 'Ayo Dosunmu' 	, 'EQP0004' );

	-- E1
	insert into Joueur values
		( 'JOU0021' , 'J1' 		, 'EQP0005' );
	insert into Joueur values
		( 'JOU0022' , 'J2' 	, 'EQP0005' );
	insert into Joueur values
		( 'JOU0023' , 'J3'		, 'EQP0005' );
	insert into Joueur values
		( 'JOU0024' , 'J4'	, 'EQP0005' );
	insert into Joueur values
		( 'JOU0025' , 'J5' 	, 'EQP0005' );

	-- EA1
	insert into Joueur values
		( 'JOU0026' , 'JA1' 		, 'EQP0006' );
	insert into Joueur values
		( 'JOU0027' , 'JA2' 	, 'EQP0006' );
	insert into Joueur values
		( 'JOU0028' , 'JA3'		, 'EQP0006' );
	insert into Joueur values
		( 'JOU0029' , 'JA4'	, 'EQP0006' );
	insert into Joueur values
		( 'JOU0030' , 'JA5' 	, 'EQP0006' );

