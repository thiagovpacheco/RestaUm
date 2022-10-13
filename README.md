Olá, este programa mostra a resolução do jogo de tabuleiro: Resta Um.

Regras do jogo:
A peça escolhida neste caso, pela CPU, deve saltar sobre outra peça, fazendo movimentos na horizontal ou na vertical, e deve chegar num espaço vazio.
A peça que foi pulada sai do jogo. Só é possível retirar uma peça por vez. O jogo termina quando restar somente uma peça ou não for possível fazer movimentos.

Como o jogo é resolvido:
Foi preciso gerar uma matriz 7x7, ou seja uma matriz bidimensional, mas nem todos os espaços devem ser preenchido.
Há quatro quadrantes nas pontas, os quais não serão utilizados no game. Utilizei o sinal de menos para simbolizá-los.
Neste caso, por ser um algoritmo de método guloso, será feito 31 movimentos, o mínimo possível para resolver o algoritmo.

