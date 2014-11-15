from xml.dom.minidom import parseString
import urllib.request
import time
import sys

from TwitterAPI import TwitterAPI

    
def tweetWeather(url):
    xml = urllib.request.urlopen(url)
    dom = parseString(xml.read())

    temperatures = dom.getElementsByTagName("temperature")
    windSpeeds = dom.getElementsByTagName("windSpeed")
    locationnames = dom.getElementsByTagName("name")
    locationname = locationnames[0].firstChild.nodeValue.strip()

    weatherStatus = "There is " + temperatures[0].attributes["value"].value + " C in " + locationname + " and a " + windSpeeds[0].attributes["name"].value.lower()
    print(weatherStatus)

    api = TwitterAPI("TsCwojpT0cJkXGsSR24VANpTy",
                     "GtMZ7VGSqe2DBG3MSZzIlvAhTD1MM25R5XSmAUu29VAjQPeWsj",
                     "2876468957-g0d0RGxiIazTM0tb7bYtUEsxytQLQg9CdWiUu9l",
                     "OL2eV0eEJw63R2idTHJLcjUOnQYJAZU7aU7vJus1bIoYW")
    r = api.request('statuses/update', {'status': weatherStatus})
    print(r.status_code)
    return

yrURL = "http://www.yr.no/place/Sweden/J%C3%A4mtland/%C3%85re/forecast.xml"
if (len(sys.argv) > 1) and ("http" in sys.argv[1]):
    yrURL = sys.argv[1]

while True:
    tweetWeather(yrURL)
    time.sleep(60)


