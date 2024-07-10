## Requisitos

### Requisitos Funcionais

1. O usuário cadastra uma viagem informando:
    - Local de destino
    - Data de início
    - Data de término
    - E-mails dos convidados
    - Nome completo e endereço de e-mail do criador

2. O criador da viagem recebe um e-mail para confirmar a nova viagem através de um link. Ao clicar no link:
    - A viagem é confirmada
    - Os convidados recebem e-mails de confirmação de presença
    - O criador é redirecionado para a página da viagem

3. Os convidados, ao clicarem no link de confirmação de presença, são redirecionados para a aplicação, onde devem inserir seu nome (além do e-mail que já estará preenchido). Assim, estarão confirmados na viagem.

4. Na página do evento, os participantes da viagem podem adicionar links importantes, como:
    - Reserva do Airbnb
    - Locais para serem visitados
    - Outros links relevantes

5. Ainda na página do evento, o criador e os convidados podem adicionar atividades que ocorrerão durante a viagem, especificando:
    - Título
    - Data
    - Horário

6. Novos participantes podem ser convidados dentro da página do evento através do e-mail e devem passar pelo fluxo de confirmação como qualquer outro convidado.

### Projeto usando [Spring Initializr](https://start.spring.io/)
    - Spring Web
    - Flyway
    - Dev Tools
    - Lombok
    - JPA
    - H2 Database
