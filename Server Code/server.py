import socket
import sys
import pyautogui
import time


def data_processing(data):
	global x
	global y
	print("DATA: ", data)
	time.sleep(5)
	if(data == 'Click'):
		perform_click()
	else:
		values = data.split()
		print("Values:", values)
		direction = values[1]
		print("direction:", direction)
		val = int(float(values[0]))
		print("Val:", val)
		if(direction=='Up' or direction == 'Down'):
			y = val*50
		elif(direction == 'Right' or direction == 'Left'):
			x = val*50
		else:
			print("Error Value")
			return
		perform_movement(x,y)
	return
def perform_click():

	pyautogui.click()

def perform_movement(x,y):
	pyautogui.dragRel(x, y, duration=2)

def connection():	
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	sock.bind(('192.168.110.1', 8080))
	sock.listen(1)
	print("listening")
	#while True:
	n = 1
	# Wait for a connection
	print(sys.stderr, 'waiting for a connection...')
	connection, client_address = sock.accept()

	try:
		print('connection from', client_address)
		# Receive the data in small chunks and retransmit it
		while(True):
			current_pos = pyautogui.position()
			data = connection.recv(4096)
			#print("received", data)
			n = n + 1
			if(len(data.decode('utf-8'))>0):
				data_processing(data.decode('utf-8'))

	finally:
		# Clean up the connection
		connection.close()
	return
x = 0
y = 0
connection()