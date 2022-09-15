package com.example.kotlinapplication

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class DocumentProcess {


companion object {
        fun readFile(){
            val path = "D:\\KotlinApplication\\app\\src\\main\\assets\\country.txt"
            val file = File(path)
            val fileInputStream = FileInputStream(file)
            var line: String? = ""

            //分行读取
            val buffreader = BufferedReader(InputStreamReader(fileInputStream))
            while (buffreader.readLine().also { line = it } != null) {
            //      content += "$line;" <item>C语言</item>
            //      <item>@array/AD</item>

                val replace = line?.substring(86)?.replace("name =", "<string-array name=\"")
                    ?.replace(",enName =", "\">\n" +
                            "    <item>@string/")
                    ?.replace(",code = ", "</item>\n" +
                            "    <item>").plus("</item>\n" +
                            "</string-array>")
                val indexOf1 = replace?.indexOf("@string/")+8
                val indexOf2 = replace?.indexOf("</item>")
                val substring = replace.substring(indexOf1, indexOf2)

                val indexOf3 = replace?.indexOf("name=\"")+6
                val indexOf4 = replace?.indexOf("\">")
                val substring1 = replace.substring(indexOf3, indexOf4)

                println("<item>@array/"+substring1+"</item>")
            }
        }
    }
}