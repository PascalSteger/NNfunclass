set terminal postscript eps
set output "e3.eps"
set size 2.5/5, 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot [0:30] [0:12] "e1.txt" using ($9) with lines,\
     "e1.txt" using ($10) with lines,\
     "e1.txt" using ($11) with lines,\
     "e1.txt" using ($12) with lines,\
     "e2.txt" using ($9+$10+$11+$12)/4 pt 6 lt rgb "red"