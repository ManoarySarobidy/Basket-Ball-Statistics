<%@page import="object.*,java.io.*,java.util.*"%>
<%
	Equipe one = (Equipe)request.getServletContext().getAttribute("equipe1");
	Equipe two = (Equipe)request.getServletContext().getAttribute("equipe2");
	Match match = (Match)request.getServletContext().getAttribute("match");
	Score score1 = null;
	Score score2 = null;
	DataMatch datam = null;
	Joueur possesseur = null;
	try{
		datam = (DataMatch)request.getServletContext().getAttribute("datamatch");
		possesseur = datam.getCurrent();
	}catch( NullPointerException exception ){

	}try{
		score1 = (new Score()).getPointsPerEquipe(one.getIdEquipe(),match.getIdMatch());
		score2 = (new Score()).getPointsPerEquipe(two.getIdEquipe(),match.getIdMatch());
	}catch(Exception e){
		PrintWriter outs = response.getWriter(); 
		e.printStackTrace(outs);
	}

	int rebondsEquipeA = 0;
	int rebondsEquipeB = 0;
	int rebondsTotal = 0;
	

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="./assets/dist/css/bootstrap.min.css">
	<title> Live data </title>
</head>
<body>
	<div class="container">
		<div class="title">
			<h2 class="text-center text-decoration-underline">
				Info de Match
			</h2>
		</div>
		<!-- anao classe ampisehoana azy amin'izay -->
		<div class="row">
			<div class="row equipe-vs">
				<div class="col-5">
					<p class="text text-center">
						<!-- Equipe 1 -->
						<% out.println(one.getNomEquipe()); %>
					</p>
				</div>
				<div class="col-2">
					<p class="text text-center">
						<!-- 0 - 0 -->
						<% out.println(score1.getPoints() + " - " + score2.getPoints());%>
					</p>
				</div>
				<div class="col-5">
					<p class="text text-center">
						<!-- Equipe 2 -->
						<% out.println(two.getNomEquipe() ); %>
					</p>
				</div>
			</div>

			<div class="possesseur">
				<%
					if( possesseur != null ){ %>
						Le possesseur est :
					<% out.println(possesseur.getNomJoueur());%>

					<% }
				%>
			</div>

			<div class="row match-info">
				<!-- ato no asiana ny info par joueur -->

				<!-- andao atambatra div ray daholo izay tokony hiaraka e -->

				<div class="tir-details row mb-3">
					<h2 class="text-center text-decoration-underline">
						Tire
					</h2>
					<%
						Datas tir = new Datas( match.getIdMatch() , "ACT0004" );
						Vector<Datas> datas = tir.getDatasPerAction();
						int a = 1 ;
						int length = datas.size()/2;
						// Collections.sort(datas);
						for( int i = 0; i < 2 ; i++ ){ 	%>
							<div class="col-5 offset-1">
									<table class="table">
										<tr>
											<th>
												Nom Joueur
											</th>
											<th>
												Points
											</th>
										</tr>
										<%
											for( int j = a - 1 ; j < length ; j++){ %>
												<tr>
													<td>
														<% out.println(datas.get(j).getNomJoueur());%>
													</td>
													<td>
														<!-- normalement tokony hoe 0 ny j de 5 ny a -->
														<% out.println(datas.get(j).getMarked() + " / " + datas.get(j).getAttempt()  );%>
													</td>
												</tr>
											<%
												a += 1; 
											}
										%>
									</table>	
								</div>
						<% 
							length *=2 ; }
					%>
					
				</div>
				<div class="tir-details row mb-3">
					<h2 class="text-center text-decoration-underline">
						Passe Decisive
					</h2>
					<%
						tir = new Datas( match.getIdMatch() , "ACT0001" );
						datas = tir.getDatasPerAction();
						a = 1 ;
						length = datas.size()/2;
						for( int i = 0; i < 2 ; i++ ){ 	%>
								<div class="col-5 offset-1">
									<table class="table">
										<tr>
											<th>
												Nom Joueur
											</th>
											<th>
												Points
											</th>
										</tr>
										<%
											for( int j = a - 1 ; j < length ; j++){ %>
												<tr>
													<td>
														<% out.println(datas.get(j).getNomJoueur());%>
													</td>
													<td>
														<!-- normalement tokony hoe 0 ny j de 5 ny a -->
														<% out.println(datas.get(j).getAttempt()); %>
													</td>
												</tr>
											<%
												a += 1; 
											}
										%>
									</table>	
								</div>

						<% 
							length *=2 ;
							}
					%>

				<div class="tir-details row mb-3">
					<h2 class="text-center text-decoration-underline">
						Rebonds Offensive
					</h2>
					<%
						tir = new Datas( match.getIdMatch() , "ACT0003" );
						datas = tir.getDatasPerAction();
						a = 1 ;
						length = datas.size()/2;
						for( int i = 0; i < 2 ; i++ ){ 	%>
								<div class="col-5 offset-1">
									<table class="table">
										<tr>
											<th>
												Nom Joueur
											</th>
											<th>
												Points
											</th>
										</tr>
										<%
											for( int j = a - 1 ; j < length ; j++){ 
												if( j < 5 ){
													rebondsEquipeA += datas.get(j).getAttempt();
												}else{
													rebondsEquipeB += datas.get(j).getAttempt();
												}
											%>
												<tr>
													<td>
														<% out.println(datas.get(j).getNomJoueur());%>
													</td>
													<td>
														<% out.println(datas.get(j).getAttempt()); %>
													</td>
												</tr>
											<%
												rebondsTotal += datas.get(j).getAttempt(); 	
												a += 1; 
											}
										%>
									</table>	
								</div>

						<% 
							length *=2 ;
							}
					%>
				</div>
					
				<div class="tir-details row mb-3">
					<h2 class="text-center text-decoration-underline">
						Rebonds Defensive
					</h2>
					<%
						tir = new Datas( match.getIdMatch() , "ACT0002" );
						datas = tir.getDatasPerAction();
						a = 1 ;length = datas.size()/2;
						for( int i = 0; i < 2 ; i++ ){ 	%>
								<div class="col-5 offset-1">
									<table class="table">
										<tr>
											<th>
												Nom Joueur
											</th>
											<th>
												Points
											</th>
										</tr>
										<%
											for( int j = a - 1 ; j < length ; j++){ 
												if( j < 5 ){
													rebondsEquipeA += datas.get(j).getAttempt();
												}else{
													rebondsEquipeB += datas.get(j).getAttempt();
												}
											%>
												<tr>
													<td>
														<% out.println(datas.get(j).getNomJoueur());%>
													</td>
													<td>
														<% out.println(datas.get(j).getAttempt()); %>
													</td>
												</tr>
											<%
												rebondsTotal += datas.get(j).getAttempt();
												a += 1; 
											}
										%>
									</table>	
								</div>

						<% 
							length *=2 ;
							}
					%>
				</div>
				<div class="pourcentage">
					<h2 class="pource text-center">
							Pourcentage de rebonds par equipe
					</h2>
					<table class="table">

						<th>Equipe 1</th>
						<th>Equipe 2</th>
						<tr>
							<td>
								<%	
									double per = Double.valueOf(rebondsTotal);
									out.println( (rebondsEquipeA / per) * 100 + " % ");
								%>
							</td>
							<td>
								<%
									// double per = Double.valueOf(rebondsTotal);
									out.println( (rebondsEquipeB / per) * 100  + " % ");
								%>
							</td>
						</tr>
					</table>
				</div>

			</div>

		</div>
		<footer class="footer border-top text-center">
			ETU 002032
		</footer>
	</div>

</body>
</html>