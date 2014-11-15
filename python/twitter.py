from xml.dom.minidom import parseString
import urllib.request
import time
from TwitterAPI import TwitterAPI

    
def tweetWeather():
    xml = urllib.request.urlopen("http://www.yr.no/place/Sweden/J%C3%A4mtland/%C3%85re/forecast.xml")
    dom = parseString(xml.read())

    temperatures = dom.getElementsByTagName("temperature")
    windSpeeds = dom.getElementsByTagName("windSpeed")
    locationnames = dom.getElementsByTagName("name")
    weatherStatus = "There is " + temperatures[0].attributes["value"].value + " C in " + locationnames[0].firstChild.nodeValue.strip() + " and a " + windSpeeds[0].attributes["name"].value.lower()


    api = TwitterAPI("TsCwojpT0cJkXGsSR24VANpTy",
                     "GtMZ7VGSqe2DBG3MSZzIlvAhTD1MM25R5XSmAUu29VAjQPeWsj",
                     "2876468957-g0d0RGxiIazTM0tb7bYtUEsxytQLQg9CdWiUu9l",
                     "OL2eV0eEJw63R2idTHJLcjUOnQYJAZU7aU7vJus1bIoYW")
    r = api.request('statuses/update', {'status': weatherStatus})
    print(r.status_code)
    return

while True:
    tweetWeather()
    time.sleep(60)


