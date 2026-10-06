# TPA_Grupo4

### Como usar o sistema:
1 - Execute o arquivo main e selecione o tipo de estrutura a ser criado.

2 - Gere um arquivo com os dados (opção 9). Deve ser especificado
o volume de dados desejado.

3 - Após a geração dos daos, carregue os contatos na estrutura
(opção 1).

O menu principal foi feito da forma mais intuitiva possível, com instruções claras e feedback constante.

O arquivo entrada.txt já possui 100.000 contatos, enquanto que o arquivo de entrada para a árvore binária deve ser gerado durante a execução.

Não é necessário exluir o arquivo de entrada caso deseje trocar os dados atuais por um novo conjunto.
Basta gerar e carregar os dados novamente através do menu.


### Organização:
- As classes ListaEncadeada e ArvoreBinaria estão localizadas em pastas dedicadas. Ambas implementam a interface IColecao.
- Domain contém os tipos de dados utilizados no projeto (Contato e Matrícula).
- As operações exclusivas de cada tipo de dado são implementadas na classe Sistema.java, localizada na pasta Service. Espera-se que, em uma entrega futura, a classe seja divida em duas, uma para as operações de lista e outra para as de árvore, afim de melhorar a organização geral do código e facilitar sua revisão.
