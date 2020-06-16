from firebase import firebase
import random

firebase = firebase.FirebaseApplication('https://campus-virtual-ucr.firebaseio.com/', None)

while 1:
	meal = random.randint(1, 3)
	max_meals = firebase.get('/ucr_eats/restaurant/1/meals/' + str(meal) + '/max', '')
	avail_num = random.randint(0, max_meals)
	firebase.put('/ucr_eats/restaurant/1/meals/'+ str(meal), 'available', avail_num)
	print("Updated [" + str(meal) + "][" + str(avail_num) + "/" + str(max_meals) + "]")


# data = firebase.get('/ucr_eats/restaurant/1/meals', '')

# for meal in data.each():
# 	print (meal.val)
