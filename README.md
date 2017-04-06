# test_services
Exercícios de java com springboot

- Projeto cep-service: Responsável por receber uma requisição CEP e retornar os dados de RUA, BAIRRO, CIDADE e ESTADO (dados esses "mockados" dentro da aplicação). Este projeto utiliza-se do Framework Fixture Factory reponsável por facilitar a criação de "templates" para os teste e suas diversas variações.

- Projeto address-service: Responsável por realizar o processo de CRUD. O mesmo utilizase de uma base em memória H2, com alguns registros para teste (vide arquivo data.sql) e também apresenta configurado o mesmo framework citado anteriormente Fixture Factory. (Este projeto est[a incompleto - faltando testes unitários)

- Projeto vowel-service: Responsável por retornar a primeira vogal única (ou seja que não se repete) endontrada dentro de uma dada palavra, e que esteja após uma consoante. (Este projeto também está incompleto - faltando finalizar o processo que seria responsável por retornar a "primeira vogal única" encontrada na dada palavra; assim como refatorar o código).
