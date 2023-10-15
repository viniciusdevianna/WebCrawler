package webcrawler

import groovyx.net.http.HttpBuilder
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

tissFiles.each {println it.attr("href")}

