import socket
import sys
import pyautogui
import time

def data_processing(data):
	global x
	global y
	#print("DATA: ", data)
	if(data == 'LClick'):
		perform_Lclick()
	elif(data == 'RClick'):
		perform_Rclick()
	elif(data == 'MClick'):
		perform_Mclick()
	else:
		values = data.split()
		direction = values[1]
		#print("direction:", direction)
		val = int(float(values[0]))
		#print("Val:", val)
		if(direction=='Up' or direction == 'Down'):
			y = val*50
		elif(direction == 'Right' or direction == 'Left'):
			x = val*50
		else:
			print("Error Value")
			return
		perform_movement(x,y)
	return

def perform_Lclick():
	print("Left Click")
	time.sleep(2)
	pyautogui.click(button='left', clicks=2, interval=0.25)

def perform_Rclick():
	print("Right Click")
	time.sleep(2)
	pyautogui.click(button='right', clicks=3, interval=0.50)

def perform_Mclick():
	time.sleep(2)
	pyautogui.scroll(-10)

def perform_movement(x,y):
	pyautogui.dragRel(x, y, duration=2)
	pyautogui.moveRel(x, y, duration=2)
def connection():	
	HOST = "***.**.**.*"
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	sock.bind((HOST, 8000))
	sock.listen(2)
	print("listening")
	# Wait for a connection
	print(sys.stderr, 'waiting for a connection...')
	connection, client_address = sock.accept()

	try:
		print('connection from', client_address)
		# Receive the data in small chunks and retransmit it
		while(True):
			current_pos = pyautogui.position()
			data = connection.recv(2048)
			#print("received", data)
			if(len(data.decode('utf-8'))>0):
				data_processing(data.decode('utf-8'))

	finally:
		# Clean up the connection
		connection.close()
	return
x = 0
y = 0
connection()