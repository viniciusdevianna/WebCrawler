# Acelera ZG 4
![Logo Zg Soluções](https://zgsolucoes.com.br/wp-content/uploads/2021/08/Copia-de-Copia-de-logo-horizontal.png)
## ZG-HERO Project
<p>Desenvolvido por: <b>Vinícius Vianna</b></p>

***

# :computer::spider: Webcrawler

## :question: O que é?
Este é um script de um webcrawler desenvolvido para o projeto da trilha K1-T11 do Acelera ZG.
O objetivo era extrair informações e baixar arquivos do [site da ANS](https://www.gov.br/ans/pt-br).

## :newspaper: O que está planejado?
As tasks que o script deve cumprir são:
- Baixar os aquivos da documentação do padrão TISS (Troca de Informações na Saúde Suplementar), na versão mais recente. :check_mark:
- Acessar o campo "Histórico das versões dos Componentes do Padrão TISS" e Coletar, na tabela, os dados de competência, publicação e início de vigência a partir da competência de data jan/2016; :check_mark:
- Acessar o campo "Tabelas relacionadas" e baixar a "Tabela de erros no envio para a ANS". :check_mark:

## :gear: Como utilizar?
Os arquivos que já foram gerados ou baixados estão na pasta `Downloads/Arquivos_padrao_TISS`.
Para rodar o script e atualizar os arquivos você pode usar o :elephant:Gradle ou executar o script `App.groovy`.

O script irá baixar o arquivo solicitado do padrão TISS, popular o arquivo csv com o histórico das versões dos componentes e baixar a tabela de erros em `.xlsx`.

## :computer: Tecnologias e frameworks utilizados

- Groovy (v2.4.21) *compatibilidade com a biblioteca HTTPBuilder-NG*
- HTTPBuilder-NG (v1.0.4)
- Jsoup (v1.15.3)
- Gradle (v8.4)