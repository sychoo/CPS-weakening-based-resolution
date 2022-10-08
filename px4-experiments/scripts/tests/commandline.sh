if [ -z $1 ]; then
        echo "Parameter 1 is empty"
        exit 0
elif [ "${#timestamp}" -lt 10 ]; then
        echo "Please enter at least a valid date"
        echo "Example: 2018-08-14"
        exit 0
else
        echo "THIS IS THE VALID BLOCK"
fi
