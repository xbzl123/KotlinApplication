package com.example.kotlinapplication

import org.simpleframework.xml.*
import org.simpleframework.xml.core.Persister

@Root(strict = false, name = "COL")
class Col {
    @field:Attribute(name = "NAME", required = false)
    var name: String? = null

    @field:Attribute(name = "VALUE", required = false)
    var value: String? = null
}

@Root(name = "DATA_FETCH", strict = false)
class DataFetch {
    @field:Element(name = "REC", required = false)
    lateinit var REC: Rec
}

@Root(strict = false, name = "REC")
class Rec {
    @field:ElementList(entry = "COL", required = false,inline = true)
    lateinit var listCol: List<Col>
}

class SimpleXmlTest {
    companion object{
    val xmlToParse = """
        <?xml version="1.0"?>
        <!DOCTYPE DATA_FETCH SYSTEM "http://www.redata.com/Xml/DATA_FETCH.dtd">
        <DATA_FETCH>
        <REC>
        <COL NAME="SELLER4_MOBILEPHONE" VALUE=""/>
        <COL NAME="SELLER_EMAIL" VALUE=""/>
        <COL NAME="SELLER_CITY" VALUE=""/>
        <COL NAME="SELLER3_HOMEPHONE" VALUE=""/>
        <COL NAME="SELLER4_FULLNAME" VALUE=""/>
        <COL NAME="BUYER4_ADDRESS1" VALUE=""/>
        <COL NAME="BUYER3_WORKPHONE" VALUE=""/>
        <COL NAME="BUYER4_WORKPHONE" VALUE=""/>
        </REC>
        </DATA_FETCH>
    """.trimIndent()
    }

    fun main(agrs:Array<String>){

    }
}