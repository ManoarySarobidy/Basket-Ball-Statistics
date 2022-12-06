create table Equipe(
	idEquipe varchar(50) primary key,
	nomEquipe varchar(250)
);
create table Joueur(
	idJoueur varchar(50) primary key,
	nomJoueur varchar(250),
	idEquipe varchar(50),
	foreign key (idEquipe) references Equipe(idEquipe)
);
create table Match(
	idMatch varchar(50) primary key,
	idEquipe1 varchar(50),
	idEquipe2 varchar(50),
	foreign key (idEquipe1) references Equipe(idEquipe),
	foreign key (idEquipe2) references Equipe(idEquipe)
);

create table act(
	idAct varchar(50) not null primary key,
	actName varchar(100)
);

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
	('EQP0006' , 'EA1');
-- los angeles lakers 5 players

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

create sequence actionSequence
	increment by 1
	start with 1
	minvalue 1;

create sequence matchSequence
	increment by 10
	start with 10
	minvalue 10;

create function getIdAction()
	returns int
	language plpgsql
	as 
	$$
	Declare
	id int;
	BEGIN
		return nextVal('actionSequence');
	END;
	$$;


create function getIdMatch()
	returns int
	language plpgsql
	as 
	$$
	Declare
	id int;
	BEGIN
		return nextVal('matchSequence');
	END;
	$$;

-- alaina ny joueur par equipe

-- Select 
-- from Joueur as j
-- join Equipe as e
-- on e.idEquipe = j.idEquipe;

-- create view for all shoots by players
-- Select * from joueur join (select idJoueur,points from action where idAct = 'ACT0004' order by idAction desc) as tir on tir.idJoueur = Joueur.idJoueur;

-- maka shoots rehetra fotsiny ito de andao groupena ito

create or replace view shoots ( idJoueur , idEquipe , points , idMatch)
	as 
	Select joueur.idJoueur , joueur.idEquipe , tir.points , tir.idMatch
	from joueur 
	join 
	(select idJoueur,points,idMatch,idAction from action where idAct = 'ACT0004') as tir 
	on tir.idJoueur = Joueur.idJoueur order by tir.idAction desc;

create or replace view passeDecissive( idJoueur , idEquipe , points , idMatch )
	as
	Select joueur.idJoueur , joueur.idEquipe , tir.points , tir.idMatch
	from joueur 
	join 
	(select idJoueur,points,idMatch from action where idAct = 'ACT0001') as tir 
	on tir.idJoueur = Joueur.idJoueur;

create or replace view rebondOffensive( idJoueur , idEquipe , points , idMatch )
	as
	Select joueur.idJoueur  , joueur.idEquipe , tir.points , tir.idMatch
	from joueur 
	join 
	(select idJoueur,points,idMatch from action where idAct = 'ACT0003') as tir 
	on tir.idJoueur = Joueur.idJoueur;

create or replace view rebondDefensive( idJoueur , idEquipe , points , idMatch )
	as
	Select joueur.idJoueur , joueur.idEquipe , tir.points , tir.idMatch
	from joueur 
	join 
	(select idJoueur,points,idMatch from action where idAct = 'ACT0002') as tir 
	on tir.idJoueur = Joueur.idJoueur;

-- inona no tokony alaiko
-- alaiko fotsiny ny dernier joueur n-tir de zay ftsn

create or replace view ScorePerEquipe
	as
	Select idEquipe , sum(points) as points , idMatch
	from shoots
	group by idEquipe , idMatch;

-- maka ny points different de 0 rehetra
create or replace view scoredPoint 
	as
	Select idJoueur ,idEquipe , points , idMatch
	from shoots
	where points != 0

-- Select idEquipe ,count(*) , idMatch from 
-- 	(Select * from scoredPoint where idJoueur = 'JOU0004' and idMatch = 'MTH0001') as ta

-- 	group by idEquipe , idMatch;

-- -- mila mamorona an'ilay total fotsiny
-- -- rehefa anao an'ilay total de alaina fotsiny ilay avy ao amin'ny total

-- -- azo ito ny total de
-- Select idEquipe ,count(*) , idMatch from
-- 	(select idEquipe , points , idMatch from shoots where idJoueur = 'JOU0004' and idMatch = 'MTH0001') as ta

-- 	group by idEquipe , idMatch;

-- Mamorona view akana an'ilay passe decissive pour chaque joueur et par match

-- satria lasa mahazo de aleo soloina idEquipe ilay izy
-- Select e.nomJoueur,sum(
-- 	CASE
-- 		WHEN points != 0 then 1
-- 		else 0
-- 	END
-- ) as made,
-- CASE
-- 	WHEN totals is null then 0
-- 	else totals
-- END,
-- idEquipe
-- from(
-- 	Select  p.idJoueur , p.idMatch , sum(
-- 		CASE
-- 			WHEN points > 0 then 1
-- 			else 0
-- 		END
-- 	)  as points , count(*) as totals
-- 	from action as p
-- 	where p.idMatch = 'MTH0001' and idAct = 'ACT0001'  group by idJoueur , idMatch
-- ) as s
-- RIGHT OUTER JOIN (Select * from joueur where idEquipe in (Select idEquipe1 from match where idMatch = 'MTH0001' UNION Select idEquipe2 from match where idMatch = 'MTH0001')
-- ) as e on e.idJoueur = s.idJoueur group by e.nomJoueur,totals,idEquipe order by idEquipe;












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



	-- select idMatch,idEquipe1 from match where (idMatch,idEquipe1) not in (Select idMatch,idEquipe from ScorePerEquipe) UNION 
	-- select idMatch,idEquipe2 from match where (idMatch,idEquipe2) not in (
	-- 	Select idMatch,idEquipe from ScorePerEquipe);

	create view deafult_score
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



-- Select * from joueur where idEquipe in (Select idEquipe1 from match where idMatch = 'MTH0001' UNION Select idEquipe2 from match where idMatch = 'MTH0001')

-- Select idEquipe1 from match where idMatch = 'MTH0001' UNION Select idEquipe2 from match where idMatch = 'MTH0001';
-- mamorona view akana an'ilay rebondOffensive

-- fonction akana anilay passe decisive
create or replace function getDecissivePass ( idj varchar(50) , idm varchar(50))
	returns table( idJoueur varchar(50), points bigint , idMatch varchar(50) )
	language plpgsql
	as
	$$
	BEGIN
		RETURN QUERY Select ta.idJoueur , count(*) as points, ta.idMatch
			from (Select * 
					from passeDecissive as p
					where p.idJoueur = 'JOU0005' and p.idMatch = 'MTH0001') as ta 
			group by ta.idJoueur,ta.idMatch;
	END;
	$$;