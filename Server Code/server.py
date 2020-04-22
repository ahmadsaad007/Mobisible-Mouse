import socket
import sys
import pyautogui



def data_processing(data):
	global x
	global y
	print("x",x)
	print(y)
	if(data == "Click"):
		perform_click()
	else:
		values = data.split()
		direction = values[1]
		val = values[0]
		if(direction=="Up" or direction == "Down"):
			y = val*2
		elif(direction == "Right" or direction == "Left"):
			x = val*2
		else:
			print("Error Values")
		perform_movement(x,y)
	return
def perform_click():
	pyautogui.click(clicks=2, interval=0.0, button='right')

def perform_movement(x,y):
	pyautogui.dragRel(x, y, duration=1)

def connection():	
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	sock.bind(('192.168.110.1', 8000))
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
		while(n<5):
			current_pos = pyautogui.position()
			data = connection.recv(4096)
			print("received", data)
			n = n + 1
			data_processing(data)

	finally:
		# Clean up the connection
		connection.close()
		#n = 1
	return
x = 0
y = 0
connection()
#conn, addr = serv.accept()
#print("server connection successful.")
#from_client = ''
#while True:
#    data = conn.recv(4096)
#    if not data: break
#    from_client += data
#    print(from_client)
#    conn.send("I am SERVER<br>")
#conn.close()
#print('client disconnected')
  