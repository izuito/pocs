#language: pt
Funcionalidade: Manutenção de Throttling Tier

  Esquema do Cenário: "<Cenario>" - Criacao de Throttling Tier - "<Descricao>"
      Dado que seja informado os dados: "<Name>", "<RequestCount>", "<UnitTime>", "<TierLevel>", "<TierPlan>", "<StopOnQuotaReach>".
    Quando executar o servico na url "<Endpoint>".
     Entao o retorno sera "<HttpStatus>".

    Exemplos: 
      | Cenario | Descricao    | Name     | RequestCount | UnitTime | TierLevel | TierPlan | StopOnQuotaReach | Endpoint                    | HttpStatus |
      | C.01    | Novo         | TestTier |            5 |    60000 | api       | FREE     | true             | http://localhost:8080/tiers |        201 |
      | C.02    | Ja existente | TestTier |            5 |    60000 | api       | FREE     | true             | http://localhost:8080/tiers |        409 |
      | C.03    | Campo nulo   |          |              |          | api       | FREE     | true             | http://localhost:8080/tiers |        400 |
