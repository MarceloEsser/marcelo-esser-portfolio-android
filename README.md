#  Sobre
O Objetivo do projeto original é auxiliar aos passagerios de ônibus de Canoas, mostrando os orários de partida tanto sentido bairro centro quanto centro bairro.
Foi desenvolvido com base em um site existente de uma das empresas responsáveis pelo transporte público em Canoas (SOGAL).
A ideia surgiu no momento em que eu notei pessoas esperando há tempos o seu ônibus no ponto, indo para o trabalho ou voltando do trabalho, além do site que não é muito acessível para pessoas sem internet fora de casa
a empresa não fornecia nenhum tipo de suporte para consulta da tabela de horários. Foi então que criei um projeto simples, que funciona tanto sem quanto com internet
para que pessoas sem acesso à rede possa consultar os horários de seus ônibus.

# Projeto

O projeto foi construído seguindo algumas recomendações e alguns padrões.
Modularizado para melhorar a desacoplação de código e/ou features inteiras, com single activity architeture e com guia de arquitetura recomendada pelo a.developers.
O Projeto contém também offline first, evitando loaders desnecessários e aumentando o desempenho da aplicação. Foi feito também para se adaptar bem ao dark mode, muito utilziado pelos usuários.

## Sobre os módulos:

### App
Módulo principal do app, onde eu mantenho a sua base, como o Application, a MainActivity e a principal/tela inicial. Contém também o módulo principal de injeção de dependencias com Koin.
Testes feitos na viewModel para garantir que a chamada para mostrar as linhas esteja alinhada, foram testados: LiveData da lista de linhas, LiveData de erro e método para pesquisa de linha por Nome e código da linha.

### Commons
Módulo com com as classes de auxilio, como por exemplo a BaseFragment.kt onde é feito o controle de databinding, loader e mensagens genéricas

### Core
Módulo com o coração do app, com a camada de repositório onde é feito as buscas tanto do banco de dados local quando numa API Rest. Para isso foram usados, basicamente, Retrofit e Room.
A Clase resopnsável por esse controle é a DataBoundResource.kt inspirada no network bound resource, antes feito com LiveData. Essa clase, busca do banco de dados, avalia a necessidade de buscar da api, se necessário, busca da api e salva no banco de dados
de forma asyncrona para não afetar negativamente a experiência do usuário com loaders e travamendo te telas.
Este móddulo contem os testes da DataBoundResource.kt para garantir que todos os métodos estejam sendo chamados no momento certo e retornando os devidos dados.

### Feature - Schedules
Módulo de feature criado para desacoplar e desenvolver features novas, a usada como exemplo é a feature que mostra os horários das linhas selecionadas.
Utilizando da mesma arquitetura e testes, o módulo foi desenvolvido também com offline first para que o usuário consiga ver seus horários mesmo com a internet fraca ou até mesmo sem.
Testes realizados na viewModel para verificar a integridade das LiveDatas, foram testados: LiveData da LineSchedule, onde contém os horários (nos dias úteis, sábados e domingos) e LiveData de erro
