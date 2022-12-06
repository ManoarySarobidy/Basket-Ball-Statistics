<%@page import="object.*,java.sql.Connection,java.util.*,frame.*,java.io.*"%>
<%
	Equipe equipe = new Equipe();
	Vector<Equipe> equipes = equipe.getAllEquipe();
	Match match = new Match();
	match.setTable("listMatch");
	Vector<Object> listmatch = null;
	try{
		listmatch = match.select();
	}catch (Exception e) {
		PrintWriter writer = response.getWriter();
		e.printStackTrace(writer);
	}

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="./assets/dist/css/bootstrap.min.css">
	<title> Bienvenue </title>
</head>
<body>

	<!-- manao formulaire isafidianana ny equipe roa ifaninana -->
	<div class="container">
		<div class="form">
			<form method="POST" action="init">
				<div class="mb-3">
					<label for="eqp1" class="form-label">
						Choose First Team :
					</label>
					<select class="form-select" name="equipe1" id="eqp1">
						<option selected disabled hidden>Choose first Team</option>
						<%
							for( int i = 0 ; i < equipes.size() ; i++ ){ %>
								<option value="<% out.println(equipes.get(i).getIdEquipe());%>">
									<% out.println(equipes.get(i).getNomEquipe());%>
								</option>
							<% }
						%>
					</select>

				</div>
				<div class="mb-3">
					<label for="eqp2" class="form-label">
						Choose Second Team :
					</label>
					<select class="form-select" name="equipe2" id="eqp2">
						<option selected disabled hidden>Choose Second Team</option>
						<%
							for( int i = 0 ; i < equipes.size() ; i++ ){ %>
								<option value="<% out.println(equipes.get(i).getIdEquipe());%>">
									<% out.println(equipes.get(i).getNomEquipe());%>
								</option>
							<% }
						%>
					</select>

				</div>
				<input type="submit" value="Begin the match" class="btn btn-primary" >
			</form>
		</div>
		<div class="match-listers">
			<h2 class="title text-center">
				Listes des precedents matchs
			</h2>
			<ul class="list-group table-hover">
				<%
					for( int i = 0 ; i < listmatch.size() ; i++ ){ %>
						<li class="list-group-item d-flex justify-content-between align-items-center hover">
							<% out.println( ((Match)listmatch.get(i)).getIdEquipe1() + " VS " + ((Match)listmatch.get(i)).getIdEquipe2() ); %>
							<a class="links btn btn-primary" href="see?idMatch=<% out.println(((Match)listmatch.get(i)).getIdMatch());%>">
								See the result
							</a>
						</li>
				<%	}
				%>
					
			</ul>

		</div>
	</div>
	<script type="text/javascript" src="./assets/dist/js/bootstrap.min.js"></script>
</body>
</html>