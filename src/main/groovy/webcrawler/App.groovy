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

Elements tableRows = padraoTissPage.select("tbody > tr")
String componenteComunicacaoLink = tableRows.find {
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
