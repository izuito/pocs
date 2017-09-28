#language: pt
Funcionalidade: Manutenção de Throttling Tier

  Esquema do Cenário: "<Cenario>" - Criacao de um novo Throttling Tier
    Dado que seja informado os dados do Throttling Tier abaixo:
      | name  | description                     | tierLevel | requestCount | unitTime | tierPlan | stopOnQuotaReach |
      | TTier | Allows 5 request(s) per minute. | API       |            5 |    60000 | FREE     | true             |
    Quando executar o servico de criacao.
    Entao o servico retornara o "<HttpStatus>".

    Exemplos: 
      | Cenario | TierName | HttpStatus |
      |       1 | TTier    |        201 |
