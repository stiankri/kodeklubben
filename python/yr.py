#!/usr/bin/env python3

from xml.dom.minidom import parseString
import urllib.request
xml = urllib.request.urlopen("http://www.yr.no/sted/Norway/Buskerud/Hol/Raggsteindalen/varsel.xml")
dom = parseString(xml.read())

temperatures = dom.getElementsByTagName("temperature")
symbols = dom.getElementsByTagName("symbol")

print("Temperaturen på hytta er: " + temperatures[0].attributes["value"].value + " grader")

for symbol in symbols:
    if "snø" in symbol.attributes["name"].value.lower():
        print("Det skal SNØ!")
        break