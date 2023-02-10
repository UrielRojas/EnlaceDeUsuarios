import cv2
import imutils
from os.path import dirname, join
import numpy as np
from PIL import Image
import base64
import io


def Deteccion_obj(data):
	#Decodifica el array de mapa de bits
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data,np.uint8)
    imagen_raw = cv2.imdecode(np_data,cv2.IMREAD_UNCHANGED) #Genera la imagen cruda

    #Lee los entrenamientos como clasificación en cascada
    VarDetec = cv2.CascadeClassifier(join(dirname(__file__), "Coffee_Mattee[3.1-10].xml"))
    VarDetec2 = cv2.CascadeClassifier(join(dirname(__file__), "Gansito[3.2-11].xml"))
    VarDetec3 = cv2.CascadeClassifier(join(dirname(__file__), "Sabritas_Naturales[2.7-8].xml"))
    VarDetec4 = cv2.CascadeClassifier(join(dirname(__file__), "Whiskas[2.7-7].xml"))		#Escala de 9:16
    VarDetec5 = cv2.CascadeClassifier(join(dirname(__file__), "cascade.xml"))

    frame = imutils.resize(imagen_raw, width=100)				#Le da un reescalado de 100p
    
    
    objeto = 'No objeto'
    lista_objeto = []

    while True:
        grises = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) 	#La imagen la pasa a escala de grises
        

        #Hace la detección multiescala de la imagen en escala de grises
        Detect_obj = VarDetec.detectMultiScale(grises, scaleFactor = 1.3, minNeighbors = 10)
        Detect_obj2 = VarDetec2.detectMultiScale(grises, scaleFactor = 3.2, minNeighbors = 11)
        Detect_obj3 = VarDetec3.detectMultiScale(grises, scaleFactor = 2.7, minNeighbors =8)
        Detect_obj4 = VarDetec4.detectMultiScale(grises, scaleFactor = 2.7, minNeighbors = 7, maxSize = (50,58))
        Detect_obj5 = VarDetec5.detectMultiScale(grises, scaleFactor = 1.1, minNeighbors = 10 )

        #Estos for son para detectar el objeto en conjunto con la función anterior "DetectMultiscale"
        for (x,y,w,h) in Detect_obj:
            objeto = 'Coffe_mate'
            lista_objeto.append(objeto)
			
        for (x,y,w,h) in Detect_obj2:
            objeto = 'Gansito'
            lista_objeto.append(objeto)
			
            
        for (x,y,w,h) in Detect_obj3:
            objeto = 'Sabritas_Naturales'
            lista_objeto.append(objeto)
				
        for (x,y,w,h) in Detect_obj4:
            objeto = 'Whiskas'
            lista_objeto.append(objeto)

        for (x,y,w,h) in Detect_obj5:
            objeto = 'Capsun'
            lista_objeto.append(objeto)
        
        lista_objeto_nueva = []
        
        #Borrará todas las cadenas repetidas de la lista
        for i in lista_objeto:
            if i not in lista_objeto_nueva:
                lista_objeto_nueva.append(i)
        
        #Si el algoritmo no detecta nada, se le manda una cadena "No objeto"
        if objeto == 'No objeto':
        	lista_objeto_nueva.append(objeto)

        #Si es objeto es diferente de "Nada", se cierra el ciclo While
        if objeto != None:
            break
    
    Cadena = " ".join(lista_objeto_nueva)    
   		
    return Cadena	#Regresa el objeto encontrado
