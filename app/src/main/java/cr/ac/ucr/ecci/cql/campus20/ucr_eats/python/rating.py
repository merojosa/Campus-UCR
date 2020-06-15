from firebase import firebase
import random

firebase = firebase.FirebaseApplication('https://campus-virtual-ucr.firebaseio.com/', None)

while 1:
	avail_num1 = round(random.uniform(1.0, 5.0), 1)
	avail_num2 = round(random.uniform(1.0, 5.0), 1)
	firebase.put('/ucr_eats/restaurant/1/', 'rating', avail_num1)
	firebase.put('/ucr_eats/restaurant/2/', 'rating', avail_num2)
	print("Rating SodaU [" + str(avail_num1) + "]")
	print("Rating PLaza [" + str(avail_num2) + "]")

