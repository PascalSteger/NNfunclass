set terminal postscript eps
set output "ea6.eps"
set size 2.5/5., 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot [0:9] [0:6]  "ea1.dat" using ($21) with lines,\
     "ea1.dat" using ($22) with lines,\
     "ea1.dat" using ($23) with lines,\
     "ea1.dat" using ($24) with lines,\
     "ea2.dat" using ($21+$22+$23+$24)/4 pt 6 lt rgb "red"