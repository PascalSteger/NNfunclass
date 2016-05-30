set terminal postscript eps
set output "ea.eps"
set size 2.5/5., 1.5/3.
set style line 5 lt rgb "red" lw 3

unset key
unset xlabel
unset ylabel
#unset ytics
#plot [-3.14:3.14] sin(x)
plot "ea.txt" using ($1):($2) with points ls 5