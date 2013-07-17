Instruções:
-

Para executar:
<pre>
git clone https://github.com/dancastellani/bbb-paredao
cd bbb-paredao
./configure
make
</pre>

Após rodar, acesse:
- Para realizar a votação: http://localhost:9090/
- Para ver o Resumo dos votos: http://localhost:9090/resumo.xhtml

Solução
-
Cliente: HTML + CSS + Javascript + JSF (resumo apenas)

Servidor:
  - Java
  - Maven
  - Jetty ou Tomcat6
  - JDBC
  - Flyway
  - Quartz
  - TestNG + Mockito

Banco de Dados:
  - HSQL ou Postgres

Observações
-

1. Votação muito acessada.
Busquei fazer a parte de votação o mais leve possível, pois essa parte deverá ter muitos acessos.
Assim, ela foi feita usando um servlet que é consumido por javascript (Jquery) pelo cliente.
Fiz essa parte assim, sem JSF para que o ciclo JSF que é pesado não seja executado, nem sejam armazenados em memória dados sobre a requisição do cliente.

2. Resumo da votação
Para o resumo da votação eu usei JSF, já que essa parte é usada apenas por uma equipe interna.

3. Para bloquear o acesso a máquinas, que imagino que a preocupação seja de DoS ou votação automática, usei como Throttle o DoSFilter do Jetty, que pode ser configurado com o número de acessos por máquina, tempo, etc. Esse filtro utiliza o IP do cliente para verificar a quantidade de acessos. Por padrão estou considerando 30 chamadas por segundo por cliente antes de ser aplicado o Throttle. 
Para garantir que apenas humanos usem, poderia ser utilizado captcha.

4. A aplicação segue MVC, Service Layer e DAO, 

5. O Banco de dados padrão está sendo HSQL em memória para facilitar a utilização tendo menos dependências para a instalação. Mas a aplicação está pronta para usar Postgres também. Para isso, devem ser feitas alterações em algumas queries: (1) uma query votos por hora que utiliza uma função de data do HSQL que não é presente no postgres; (2) nas migrações também são utilizadas funções de data que não são compatíveis entre HSQL e Postgres. Em ambos os casos adaptações poderiam ser feitas para a aplicação ter migrações e executar consultas cross-configuração.

6. O Maven gerencia todo o ciclo de construção e execução (com Jetty). Foram criados perfis de desenvolvimento e produção.

7. Não foi utilizado framework para acesso ao banco de dados e controle transacional. O acesso ao Banco foi feito utilizando JDBC apenas. Não foi utilzado hibernate ou outro ORM pois não valeria a pena para uma aplicação deste tamanho com poucas entidades que a principal funcionalidade é a votação e teria que ser otimizada, provavelmente, executando as consultas como queries sem ORM. Essa parte poderia ser melhorada usando o Framework Spring para fazer as consultas.

8. Para a Migração da base de dados e alimentação dos dados de teste e dados fake para inicializar a aplicação foi utilizado o Flyway.

9. Os votos são armazenados em memória quando são realizados e são salvos no banco de dados em batch periodicamente.
Foi feito dessa forma para deixar mais leve a votação. Os votos são salvos a cada segundo, mas este tempo é configurável.
Para implementar esta tarefa foi utilizado o Quartz.

10. Para os testes foi utilizado o TestNG + Mockito.

11. O deploy pode ser feito em Tomcat 6 ou Jetty. 

12. Foi realizado teste de carga com ab da Apache no Ubuntu, obtendo mais de 1000 acessos por segundo em ambas as páginas e web services.

13. Para aumentar ainda mais a disponibilidade, poderiam ser utilizados vários servidores com um balancer na frente dividindo as requisições entre eles.
Para facilitar o deploy e teste, foi utilizado o Jetty Embed no Maven. Mas em ambiente de produção outro servidor deveria ser utilizado, configurado para isso.



