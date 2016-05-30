set terminal postscript eps
set output "ea4.eps"
set size 2.5/5., 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot  [0:9] [0:6]  "ea1.dat" using ($13) with lines,\
     "ea1.dat" using ($14) with lines,\
     "ea1.dat" using ($15) with lines,\
     "ea1.dat" using ($16) with lines,\
     "ea2.dat" using ($14+$15+$16+$17)/4 pt 6 lt rgb "red"