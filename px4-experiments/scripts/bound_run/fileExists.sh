# FILE=/etc/resolv.conf
# if [ -f "$FILE" ]; then
#     echo "$FILE exists."
# else 
#     echo "$FILE does not exist."
# fi
DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness
if [[ $(ls $DIR/*config_101_*.csv) ]]; then
    echo "there are files"
else
    echo "no files found"
fi
