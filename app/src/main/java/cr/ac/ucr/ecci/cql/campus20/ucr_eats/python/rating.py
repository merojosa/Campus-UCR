from firebase import firebase
import random

firebase = firebase.FirebaseApplication('https://campus-virtual-ucr.firebaseio.com/', None)

while 1:
	avail_num = round(random.uniform(1.0, 5.0), 1)
	firebase.put('/ucr_eats/restaurant/1/', 'rating', avail_num)
	print("conoce [" + str(avail_num) + "]")

