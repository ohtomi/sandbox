#!/bin/bash
# -*- encoding: utf-8 -*-

echo
echo '--- importing in Python ---'
echo
python main.py

echo
echo '--- importing in Jython ---'
echo
java -cp jython-standalone-2.7.0.jar:JyNI-2.7-alpha.2.3.jar org.python.util.jython main.py
