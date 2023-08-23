
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return true
  } else {
    return true
  }
}


function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("username length should between 3-20 characters"))
  } else {
    callback()
  }
}


function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please Enter name"))
  } else if (value.length > 30) {
    callback(new Error("Name length should between 1-30 characters"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == "") {
    callback(new Error("Please enter mobile number"))
  } else if (!isCellPhone(value)) {
    callback(new Error("Invalid mobile number"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('Please Enter ID Number'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('Invalid ID Number'))
  }
}