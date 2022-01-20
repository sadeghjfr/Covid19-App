package com.sadeghjfr22.covid19.data.model

class NewsModel (var status : String,
                      var news : ArrayList<News>,
                      var page : Int)

class News (var id:String,
                 var title:String,
                 var description:String,
                 var url:String,
                 var author:String,
                 var image:String,
                 var published:String,
                 var expended:Boolean)