# Sudoku
Projet de logique : formalisation et utilisation de sat solveur pour le jeu Sudoku

Le dossier du code  est sur la branche master( on s'est apercu tardivement de cela et ne sachant plus quoi elle est restée comme ca) merci de retrouver l'intégralité de notre code là dessus .
Toutefois voici un guide pour l'utilisation de nos classes .
Il y a 7 classes et 1 classe test .
En utilisant minisat seules les clases CDimacs, MinisatCommande et SolutionAfficheur seront utiliséees dans cet ordre 
 1* CDimacs (en 1er param le fichier de la grille attendu comme tel et en 2eme le lieu voulu du fichier Dimacs) pour générer le fichier dimacs
 2* MinisatCommande pour lancer Minisat mais on peut aussi le faire sur le terminal
 3* SolutionAfficheur pour avoir une réponse visuelle de la grille : en paramètre le fichier de la solution obtenue précédemment  

 En utilisant notre propre sat solveur (qui ne trouve malheureusement que 50 valeurs max sur 81 ) on procédera comme suit
  1* Sat3 qui permet d’obtenir le fichier dimacs et le met en même temps en 3sat : il prend en paramètres d'abord le fichier de la grille et après celui où il faudra mettre le fichier dimacs
  2* SatSolveur ( en 1er paramètre le fichier obtenu précédemment et en 2eme là où on veut mettre le fichier résultat) 
  3* Afficheur3Sat : marche de la même manière que SolutionAfficheur mais on préfère le coder séparément pour différencier un cas qui créait une erreur  

  Et après tout ça vous pouvez passer la classe ValiditeTest qui vérifie si les résultats obtenus sont conformes aux règles du Sudoku . Juste piur info il faudra mettre dans la classe Test vos fichiers de solutions obtenus.



  Pour rappel merci de retrouver tout le code sur la branche Master.et désolé pour la confusion .

  Nous ( Khalil , Claver , Edem , Amadou ) vous souhaitons bonne expérimentation.

  Merci.
