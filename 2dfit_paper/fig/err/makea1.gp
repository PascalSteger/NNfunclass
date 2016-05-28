set terminal postscript eps
set output "ea1.eps"
set size 2.5/5., 1.5/3.
#set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot [0:9] [0:6] "ea1.dat" using ($1) with lines,\
     "ea1.dat" using ($2) with lines,\
     "ea1.dat" using ($3) with lines,\
     "ea1.dat" using ($4) with lines,\
     "ea2.dat" using ($1+$2+$3+$4)/4 pt 6 lt rgb "red"