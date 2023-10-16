package webcrawler

import groovyx.net.http.HttpBuilder
import groovyx.net.http.HttpConfig
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

Document mainPage = HttpBuilder.configure {
    request.uri = "https://www.gov.br/ans/pt-br"
}.get() as Document

String areaPrestadorLink = mainPage
        .select("div#ce89116a-3e62-47ac-9325-ebec8ea95473 > div > a")
        .first()
        .attr("href")

Document prestadorPage = HttpBuilder.configure {
    request.uri = areaPrestadorLink
}.get() as Document

String areaTissLink = prestadorPage
        .select("div.card > a")
        .first()
        .attr("href")

Document areaTissPage = HttpBuilder.configure {
    request.uri = areaTissLink
}.get() as Document

Elements tissFiles = areaTissPage.select("p.callout > a")

Document padraoTissPage = HttpBuilder.configure {
    request.uri = tissFiles[0].attr("href")
}.get() as Document

Elements filesTableRows = padraoTissPage.select("tbody > tr")
String componenteComunicacaoLink = filesTableRows.find {
        it.select("td").first().html() == "Componente de Comunicação"
    }.select("td > a").first().attr("href")

HttpBuilder.configure {
    request.uri = componenteComunicacaoLink
}.get {
    String fileName = componenteComunicacaoLink.split("/").last()
    File file = new File("./Downloads/Arquivos_padrao_TISS/${fileName}")
    file.getParentFile().mkdirs()
    file.createNewFile()
    Download.toFile(delegate as HttpConfig, file)
    println "${fileName} salvo na pasta Downloads"
}

Document historicoTissPage = HttpBuilder.configure {
    request.uri = tissFiles[1].attr("href")
}.get() as Document

Elements compenteciasTableRows = historicoTissPage.select("tbody > tr")
Integer lastDateRowIndex = compenteciasTableRows.findIndexOf {
    it.select("td")
            .first()
            .html()
            .toLowerCase()
            .replace("<span>", "")
            .replace("</span>", "") == "jan/2016"
}

File historico = new File("./Downloads/Arquivos_padrao_TISS/historico.csv")
if (!historico.exists()) {
    historico.createNewFile()
    historico.with {
        it.append("Competência;Publicação;Início de Vigência;\n")
    }
}

historico.with {file ->
    compenteciasTableRows.subList(0, lastDateRowIndex).each {
        it.select("td").subList(0, 3).each { element ->
            file.append(
                    element
                            .html()
                            .toLowerCase()
                            .replace("<span>", "")
                            .replace("</span>", "")
                            + ";"
            )
        }
        file.append("\n")
    }
}

Document tabelaErrosPage = HttpBuilder.configure {
    request.uri = tissFiles[2].attr("href")
}.get() as Document

String tabelaErrosLink = tabelaErrosPage.select("p.callout > a").first().attr("href")
HttpBuilder.configure {
    request.uri = tabelaErrosLink
}.get {
    String filename = tabelaErrosLink.split("/").last()
    File file = new File("./Downloads/Arquivos_padrao_TISS/${filename}")
    file.createNewFile()
    Download.toFile(delegate, file)
}
