import glob, os

path = './'
filenames = os.listdir(path)

src_target_dict = {'two' : 2, 'three' : 3, 'four' : 4, 'five' : 5, 'six' : 6, 'seven' : 7, 'eight' : 8, 'nine' : 9, 'ten' : 10}

for fn in filenames:
	print (fn)
	for k, v in src_target_dict.items():
		# print (k)
		if str(v) in fn:
			print ("rename: " + os.path.join(path, fn) + " to " + os.path.join(fn.replace(str(v), k)))
			os.rename(os.path.join(path, fn), os.path.join(fn.replace(str(v), k)))
