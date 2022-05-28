package com.example.project_2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.FileOutputStream
import java.util.*
import java.net.URL
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        table=findViewById(R.id.table)
        loadData()
        showData()
        downloadData()
    }

    var forex:MutableList<Currency>? = null
    var kantor:MutableList<Currency>? = null
    var santander:MutableList<Currency>? = null
    var games:MutableList<Currency>? = null

    private lateinit var table: TableLayout

    fun downloadData(){
        val cd=Downloader()
        cd.execute()
    }

    fun loadData(){
        forex= mutableListOf()
        kantor= mutableListOf()
        santander= mutableListOf()
        games= mutableListOf()

        val filename="games.xml"
        val path=filesDir
        val inDir=File(path, "XML")

        if(inDir.exists()){
            val file=File(inDir, filename)

            if(file.exists()){
                val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)

                xmlDoc.documentElement.normalize()

                val items: NodeList=xmlDoc.getElementsByTagName("item")
                println("length "+items.length)


                for(i in 0 until items.length-1){
                    val itemNode: Node = items.item(i)
                    val attributes=itemNode.attributes

                    var gameId=itemNode.attributes.getNamedItem("objectid").nodeValue


                    if(itemNode.nodeType==Node.ELEMENT_NODE){
                        val elem = itemNode as Element
                        val children = elem.childNodes


                        var gamesList:MutableList<Currency>? = null
//                        var currentCode: String? = null
//                        var currentRate: String? = null
//                        var currentDate: String? = null

                        var gameName: String? = null
                        var gameImage: String? = null
                        var pubYear: String? = null

                        gamesList=games



                        for (j in 0 until children.length-1){
                            val node = children.item(j)

                            if(node is Element){
                                when(node.nodeName){
                                    "name"->{
                                        gameName=node.textContent
                                    }
                                    "thumbnail"->{
                                        gameImage=node.textContent
                                    }
                                    "yearpublished"->{
                                        pubYear=node.textContent
                                    }
                                }
                            }
                        }

                        if(gamesList!=null && gameName!=null && gameId!=null && pubYear!=null && gameImage!=null){
//                            val rates=currentRate.split(" ")
//                            val r=rates[0].toDouble()
//                            val ch=rates[1].toDouble()
//                            val pattern ="EEE, dd MMM yyyy HH:mm:ss Z"
//                            val sdf=SimpleDateFormat(pattern, Locale.ENGLISH)
//                            val d=sdf.parse((currentDate))

                                val year=pubYear.toInt()

                            val newGame = Currency(gameId, gameName, year, gameImage )
                            gamesList.add(newGame)
                        }
                    }
                }
            }
        }
    }

    fun showData(){
        table.removeAllViews()

        if(games!=null)
            showGames(games!!, "Games")

//        if(forex!=null)
//            showGames(forex!!, "Forex")
//        if(kantor!=null)
//            showGames(kantor!!, "Kantor")
//        if(santander!=null)
//            showGames(santander!!, "Santander")
    }


    @Suppress("DEPRECATION")
    private inner class Downloader: AsyncTask<String, Int, String>(){

        override fun onPostExecute(result: String?){
            super.onPostExecute(result)
            loadData()
            showData()
        }

        override fun doInBackground(vararg p0: String?): String {
            try {
//                var url = URL("https://boardgamegeek.com/xmlapi2/collection?username=loutre_on_fire")
                var url = URL("http://localhost:3000/games")

                val connection = url.openConnection()
                connection.connect()

                val lengthOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")

                if (!testDirectory.exists()) testDirectory.mkdir()

//                val fos = FileOutputStream("$testDirectory/waluty.xml")
                val fos = FileOutputStream("$testDirectory/games.xml")
                val data = ByteArray(1024)
                var count = 0
                var total: Long = 0
                var progress = 0
                count = isStream.read(data)

                while (count != -1) {
                    total += count.toLong()
                    val progress_temp = total.toInt() * 100 / lengthOfFile

                    if (progress_temp % 10 == 0 && progress != progress_temp) {
                        progress = progress_temp
                    }

                    fos.write(data, 0, count)
                    count = isStream.read(data)
                }

                isStream.close()
                fos.close()
            }  catch(e: Exception){
                return "fuck"
            }

//            catch (e: MalformedURLException) { return "Zly URL" }
//            catch (e: FileNotFoundException) { return "Brak pliku" }
//            catch (e: I0Exception) { return "wyjatek IO" }

            return "success"

        }


        fun refresh(v: View){
            downloadData()
        }

    }

    fun getImg(src:String): Bitmap? {
        val imageURL = src
        var image: Bitmap? = null
        try {
            val `in` = java.net.URL(imageURL).openStream()
            image = BitmapFactory.decodeStream(`in`)
        }
        catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }
        return image
    }


    fun showGames(games:List<Currency>,name:String) {
        val leftRowMargin = 0
        val topRowMargin = 0
        val rightRowMargin = 0
        val bottomRowMargin = 0
        var textSize = 0
        var smallTextSize = 0
        var mediumTextSize = 0

        textSize = resources.getDimension(R.dimen.font_size_small).toInt()
        smallTextSize = resources.getDimension(R.dimen.font_size_medium).toInt()
        mediumTextSize = resources.getDimension(R.dimen.font_size_big).toInt()

        val rows = games.count()
        supportActionBar!!.setTitle("nazwa uzytkownika")
        var textSpacer: TextView? = null


        val dateFormat = SimpleDateFormat("HH:mm - dd.MM.yyyy")
//        var date:Date?=null

        // -1 oznacza nagłówek
        for (i in -1..rows - 1) {
            var row: Currency? = null

            if (i < 0) {
                //nagłówek
                textSpacer = TextView(this)
                textSpacer.text = ""
            } else {
                row = games.get(i)
            }

//            if (row!=null)
//                date=row.date

            val tv = TextView(this)
            tv.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)
            tv.gravity = Gravity.LEFT
            tv.setPadding(20, 15, 20, 15)

            if (i == -1) run {
                tv.text = "indeks"
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"))
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mediumTextSize.toFloat())
            } else run({
                tv.setBackgroundColor(Color.parseColor("#f8f8f8"))
//                tv.setText(row?.code)
                tv.setText(i.toString())
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mediumTextSize.toFloat())
            })

            val tv2=ImageView(this)
//            val tv2 = TextView(this)
            if(row?.image!=null)
                tv2.setImageURI(Uri.parse(row?.image))

            if (i == -1) {
                tv2.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT)
//                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize.toFloat())
            } else {
                tv2.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT)
//                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, mediumTextSize.toFloat())


            }

//            tv2.gravity = Gravity.LEFT

            tv2.setPadding(20, 15, 20, 15)
            if (i == -1) {
                val tv2_title = TextView(this)
                tv2_title.text = "thumbnail"
                tv2.setBackgroundColor(Color.parseColor("#f7f7f7"))
            } else {
                tv2.setBackgroundColor(Color.parseColor("#ffffff"))
//                tv2.setTextColor(Color.parseColor("#000000"))
//                tv2.setText(row?.image)

//                val img= row?.image?.let { getImg(it) }
//                tv2.setImageBitmap(img)
            }

            val layCustomer = LinearLayout(this)
            layCustomer.orientation = LinearLayout.VERTICAL
            layCustomer.setPadding(20, 10, 20, 10)
            layCustomer.setBackgroundColor(Color.parseColor("#f8f8f8"))

            val tv3 = TextView(this)
            if (i == -1) {
                tv3.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT)
                tv3.setPadding(5, 5, 0, 5)
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize.toFloat())
            } else {
                tv3.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.MATCH_PARENT)
                tv3.setPadding(5, 0, 0, 5)
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            }

            tv3.gravity = Gravity.TOP

            if (i == -1) {
                tv3.text = "Name"
                tv3.setBackgroundColor(Color.parseColor("#f0f0f0"))
            } else {
                tv3.setBackgroundColor(Color.parseColor("#f8f8f8"))
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize.toFloat())
                tv3.setText(row?.getGameName() + " (" + row?.getPubYear() + ")")
            }
            layCustomer.addView(tv3)


//            if (i > -1) {
//                val tv3b = TextView(this)
//                tv3b.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT)
//
//                tv3b.gravity = Gravity.RIGHT
//                tv3b.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize.toFloat())
//                tv3b.setPadding(5, 1, 0, 5)
//                tv3b.setTextColor(Color.parseColor("#aaaaaa"))
//                tv3b.setBackgroundColor(Color.parseColor("#f8f8f8"))
//                tv3b.setText(row?.formatChange())
//                layCustomer.addView(tv3b)
//            }


            // add table row
            val tr = TableRow(this)
            tr.id = i + 1
            val trParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT)
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin)
            tr.setPadding(10, 0, 10, 0)
            tr.layoutParams = trParams

            tr.addView(tv)
            tr.addView(tv2)
            tr.addView(layCustomer)

            table.addView(tr, trParams)

            if (i > -1) {

                val trSep = TableRow(this)
                val trParamsSep = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT)
                trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin)

                trSep.layoutParams = trParamsSep
                val tvSep = TextView(this)
                val tvSepLay = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
                tvSepLay.span = 4
                tvSep.layoutParams = tvSepLay
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"))
                tvSep.height = 1

                trSep.addView(tvSep)
                table.addView(trSep, trParamsSep)
            }


        }

        val trDate = TableRow(this)
        val trParamsSep = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT)
        trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin)

        trDate.layoutParams = trParamsSep
        val tvSep = TextView(this)
        val tvSepLay = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.MATCH_PARENT)
        tvSepLay.span = 4
        tvSep.layoutParams = tvSepLay
        tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"))

        tvSep.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize.toFloat())
//        if (date!=null)
//            tvSep.text=dateFormat.format(date)

        trDate.addView(tvSep)
        table.addView(trDate, trParamsSep)
    }
}