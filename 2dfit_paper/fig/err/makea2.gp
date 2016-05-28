set terminal postscript eps
set output "ea2.eps"
set size 2.5/5., 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot  [0:9] [0:6]  "ea1.dat" using ($5) with lines,\
     "ea1.dat" using ($6) with lines,\
     "ea1.dat" using ($7) with lines,\
     "ea1.dat" using ($8) with lines,\
     "ea2.dat" using ($5+$6+$7+$8)/4 pt 6 lt rgb "red"