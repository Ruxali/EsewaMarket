package com.example.esewamarket.models

class Banner(
    val title: String,
    val imageUrl: String
) {
    override fun toString(): String {
        return "Banner [title: ${this.title}, imageUrl: ${this.imageUrl}]"
    }

    companion object{

        fun getDummyBanner()= listOf(
            Banner("Title 1","https://s3-alpha-sig.figma.com/img/fd28/e196/6b458cdbe0f1706cb7027aa96f7cee47?Expires=1691971200&Signature=qThMwPLzIPD4Y-JoJe58ItC0zhJIwr18mK7yCgk7KL12xZrXDrR7Qd9pEQCAdlm2xm8eloO~j9-jcTqapli5y1Iyao8kL1CoHanSZLiLcqFImJQdfLtcUxpXaQ53ar4fn5SlhNnkEvz6ztYRRUxYe695kckwKfBkMhIQbHrAz2D0y0yyOMLpXIJCiNMjObQGvPbiczgL82GFmzvPPebBqKye8R9etnKYO66VFDITmgSgJCbh2mr4K6vhnbFCH2Wk-I0z6esLwBHKf0f2wNzoZgFtH-ZQITh8br~dDNpWVmQJiWYxppXBYFN2rKB-DGlGNclnoAQoBEFbOEQeZO0hkA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
            Banner("Title 1","https://s3-alpha-sig.figma.com/img/fd28/e196/6b458cdbe0f1706cb7027aa96f7cee47?Expires=1693180800&Signature=OAb6lGT2ATVxh8jZgDMDz5q9rhClWInwfpwWCbfEC7Ate1MLRXj35Ztsbm0G9pofhfavimAwa77JD2dG4IoCLUckcyXS1bzHPAKNO0uESBzQpLUhoIvlt-tdGHPylcC-qKxUw~hisn6Avp4ZdVCc-tPVgnV4s3J4BxhpcfCpOwdYitlsKJYCz-~ENPbdsRh7looxnImiQZNDInus~aCE8Ieh9GV-h7rLm38us1TCH2Qg06tGnt7N0RVrMP0JDrfmYpHAgX6OFGf5aCdlkOMYmuwLAqJrDJyjAtCON8jx80KyURsy2IzWVqwdOZ-2-qgfNKubkQIto7fgwRLLBiW5tA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
            Banner("Title 1","https://s3-alpha-sig.figma.com/img/fd28/e196/6b458cdbe0f1706cb7027aa96f7cee47?Expires=1691971200&Signature=qThMwPLzIPD4Y-JoJe58ItC0zhJIwr18mK7yCgk7KL12xZrXDrR7Qd9pEQCAdlm2xm8eloO~j9-jcTqapli5y1Iyao8kL1CoHanSZLiLcqFImJQdfLtcUxpXaQ53ar4fn5SlhNnkEvz6ztYRRUxYe695kckwKfBkMhIQbHrAz2D0y0yyOMLpXIJCiNMjObQGvPbiczgL82GFmzvPPebBqKye8R9etnKYO66VFDITmgSgJCbh2mr4K6vhnbFCH2Wk-I0z6esLwBHKf0f2wNzoZgFtH-ZQITh8br~dDNpWVmQJiWYxppXBYFN2rKB-DGlGNclnoAQoBEFbOEQeZO0hkA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
            Banner("Title 1","https://s3-alpha-sig.figma.com/img/fd28/e196/6b458cdbe0f1706cb7027aa96f7cee47?Expires=1693180800&Signature=OAb6lGT2ATVxh8jZgDMDz5q9rhClWInwfpwWCbfEC7Ate1MLRXj35Ztsbm0G9pofhfavimAwa77JD2dG4IoCLUckcyXS1bzHPAKNO0uESBzQpLUhoIvlt-tdGHPylcC-qKxUw~hisn6Avp4ZdVCc-tPVgnV4s3J4BxhpcfCpOwdYitlsKJYCz-~ENPbdsRh7looxnImiQZNDInus~aCE8Ieh9GV-h7rLm38us1TCH2Qg06tGnt7N0RVrMP0JDrfmYpHAgX6OFGf5aCdlkOMYmuwLAqJrDJyjAtCON8jx80KyURsy2IzWVqwdOZ-2-qgfNKubkQIto7fgwRLLBiW5tA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
            Banner("Title 1","https://s3-alpha-sig.figma.com/img/fd28/e196/6b458cdbe0f1706cb7027aa96f7cee47?Expires=1691971200&Signature=qThMwPLzIPD4Y-JoJe58ItC0zhJIwr18mK7yCgk7KL12xZrXDrR7Qd9pEQCAdlm2xm8eloO~j9-jcTqapli5y1Iyao8kL1CoHanSZLiLcqFImJQdfLtcUxpXaQ53ar4fn5SlhNnkEvz6ztYRRUxYe695kckwKfBkMhIQbHrAz2D0y0yyOMLpXIJCiNMjObQGvPbiczgL82GFmzvPPebBqKye8R9etnKYO66VFDITmgSgJCbh2mr4K6vhnbFCH2Wk-I0z6esLwBHKf0f2wNzoZgFtH-ZQITh8br~dDNpWVmQJiWYxppXBYFN2rKB-DGlGNclnoAQoBEFbOEQeZO0hkA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
            )
    }
}