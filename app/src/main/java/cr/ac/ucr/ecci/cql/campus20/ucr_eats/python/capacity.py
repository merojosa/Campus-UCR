from firebase import firebase
import random

firebase = firebase.FirebaseApplication('https://campus-virtual-ucr.firebaseio.com/', None)

while 1:
	meal = random.randint(1, 3)
	max_cap = firebase.get('/ucr_eats/restaurant/1' + '/capacity_max', '')
	avail_num = random.randint(0, max_cap)
	firebase.put('/ucr_eats/restaurant/1/', 'capacity', avail_num)
	print("conoce [" + str(avail_num) + "/" + str(max_cap) + "]")

