from firebase import firebase
import random

firebase = firebase.FirebaseApplication('https://campus-virtual-ucr.firebaseio.com/', None)

while 1:
	max_cap1 = firebase.get('/ucr_eats/restaurant/1' + '/capacity_max', '')
	max_cap2 = firebase.get('/ucr_eats/restaurant/2' + '/capacity_max', '')
	avail_num1 = random.randint(0, max_cap1)
	avail_num2 = random.randint(0, max_cap2)
	firebase.put('/ucr_eats/restaurant/1/', 'capacity', avail_num1)
	firebase.put('/ucr_eats/restaurant/2/', 'capacity', avail_num2)
	print("Capacidad SodaU [" + str(avail_num1) + "/" + str(max_cap1) + "]")
	print("Capacidad Plaza [" + str(avail_num2) + "/" + str(max_cap2) + "]")

