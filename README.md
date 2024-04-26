Controle de Compras

## Sobre o projeto
Este aplicativo foi desenvolvido para simplificar o gerencimaneto de compras de pessoa. Permitindo um controle rápido e eficas através de uma interface fácil e moderna.

## Funcionamento do aplicativo

## Estrutura do Projeto
/manifests      # Configurações do projeto
/java           # Código-fonte principal do aplicativo
  /database     # Implementação do Banco de dados
  /extensions   # Extend Function em kotlin para converter tipos de dados
  /model        # Entidades
  /ui           # Interfaces do úsuario
/tests          # Testes automatizados, garantindo a qualidade
/res            # Layouts, imagens, fonts, menus, etc.

## Tecnolgias utilizadas
- **Android Studio**: Ambiente de execuçao do aplicativo.
- **Room**: Implementaçao do Room para persistir dados com o SQLite.
- **Binding**: Facilita o binding das view utilizadas nos layouts (Constraint-Layout).
- **Coroutines**: Utilizado para encaminhar as operaçoes do banco de dados para uma thread diferente da principal.
- **RecylcerView**: Utilizado para acoplar todas as viewholders criadas por meio do adapter.
- **Animations**: Utilizado para implementar animaçoes de remover e reposicionar o viewholder.
- **SQLite**: Executando em junçao com o Room.
- **Kotlin**: Linguagem utilizada em todo o projeto.
  

  ## Este é o ORGSAPP 
