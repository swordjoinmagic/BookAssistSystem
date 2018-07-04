// // 用于加密的js文件

// // base64的加密方法
// var Base64 = {

//     // private property
//     _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
    
//     // public method for encoding
//     encode: function(input) {
//         var output = "";
//         var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
//         var i = 0;
    
//         input = Base64._utf8_encode(input);
    
//         while (i < input.length) {
    
//             chr1 = input.charCodeAt(i++);
//             chr2 = input.charCodeAt(i++);
//             chr3 = input.charCodeAt(i++);
    
//             enc1 = chr1 >> 2;
//             enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//             enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//             enc4 = chr3 & 63;
    
//             if (isNaN(chr2)) {
//                 enc3 = enc4 = 64;
//             } else if (isNaN(chr3)) {
//                 enc4 = 64;
//             }
    
//             output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
    
//         }
    
//         return output;
//     },
    
//     // public method for decoding
//     decode: function(input) {
//         var output = "";
//         var chr1, chr2, chr3;
//         var enc1, enc2, enc3, enc4;
//         var i = 0;
    
//         input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
    
//         while (i < input.length) {
    
//             enc1 = this._keyStr.indexOf(input.charAt(i++));
//             enc2 = this._keyStr.indexOf(input.charAt(i++));
//             enc3 = this._keyStr.indexOf(input.charAt(i++));
//             enc4 = this._keyStr.indexOf(input.charAt(i++));
    
//             chr1 = (enc1 << 2) | (enc2 >> 4);
//             chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
//             chr3 = ((enc3 & 3) << 6) | enc4;
    
//             output = output + String.fromCharCode(chr1);
    
//             if (enc3 != 64) {
//                 output = output + String.fromCharCode(chr2);
//             }
//             if (enc4 != 64) {
//                 output = output + String.fromCharCode(chr3);
//             }
    
//         }
    
//         output = Base64._utf8_decode(output);
    
//         return output;
    
//     },
    
//     // private method for UTF-8 encoding
//     _utf8_encode: function(string) {
//         string = string.replace(/\r\n/g, "\n");
//         var utftext = "";
    
//         for (var n = 0; n < string.length; n++) {
    
//             var c = string.charCodeAt(n);
    
//             if (c < 128) {
//                 utftext += String.fromCharCode(c);
//             } else if ((c > 127) && (c < 2048)) {
//                 utftext += String.fromCharCode((c >> 6) | 192);
//                 utftext += String.fromCharCode((c & 63) | 128);
//             } else {
//                 utftext += String.fromCharCode((c >> 12) | 224);
//                 utftext += String.fromCharCode(((c >> 6) & 63) | 128);
//                 utftext += String.fromCharCode((c & 63) | 128);
//             }
    
//         }
    
//         return utftext;
//     },
    
//     // private method for UTF-8 decoding
//     _utf8_decode: function(utftext) {
//         var string = "";
//         var i = 0;
//         var c = c1 = c2 = 0;
    
//         while (i < utftext.length) {
    
//             c = utftext.charCodeAt(i);
    
//             if (c < 128) {
//                 string += String.fromCharCode(c);
//                 i++;
//             } else if ((c > 191) && (c < 224)) {
//                 c2 = utftext.charCodeAt(i + 1);
//                 string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
//                 i += 2;
//             } else {
//                 c2 = utftext.charCodeAt(i + 1);
//                 c3 = utftext.charCodeAt(i + 2);
//                 string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
//                 i += 3;
//             }
    
//         }
    
//         return string;
//     }
    
//     }

// // 输入一个字符串，获得加密后的字符串
// function getEncryptionCode(){
//     // 获得当前时间戳
//     var date = new Date().getTime().toString();

//     var result = "";

//     for(var i=0;i<date.length;i++){
//         result += String.fromCharCode(65+parseInt(date[i]));
//     }

//     result = Base64.encode(result);

//     return result;
// }


// 代码混淆后
var Dg1 = {        _keyStr: "\x41\x42\x43\x44\x45\x46\x47\x48\x49\x4a\x4b\x4c\x4d\x4e\x4f\x50\x51\x52\x53\x54\x55\x56\x57\x58\x59\x5a\x61\x62\x63\x64\x65\x66\x67\x68\x69\x6a\x6b\x6c\x6d\x6e\x6f\x70\x71\x72\x73\x74\x75\x76\x77\x78\x79\x7a\x30\x31\x32\x33\x34\x35\x36\x37\x38\x39\x2b\x2f\x3d",            encode: function(KZ2) {        var RLYPSVZJN3 = "";        var c4, viUWMem5, iBBhbQgM6, XAGERGoCK7, QwID8, $W9, Dtmkv$A10;        var CcUtssbA11 = 0;            KZ2 = Dg1["\x5f\x75\x74\x66\x38\x5f\x65\x6e\x63\x6f\x64\x65"](KZ2);            while (CcUtssbA11 < KZ2["\x6c\x65\x6e\x67\x74\x68"]) {                c4 = KZ2["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](CcUtssbA11++);            viUWMem5 = KZ2["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](CcUtssbA11++);            iBBhbQgM6 = KZ2["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](CcUtssbA11++);                XAGERGoCK7 = c4 >> 2;            QwID8 = ((c4 & 3) << 4) | (viUWMem5 >> 4);            $W9 = ((viUWMem5 & 15) << 2) | (iBBhbQgM6 >> 6);            Dtmkv$A10 = iBBhbQgM6 & 63;                if (isNaN(viUWMem5)) {                $W9 = Dtmkv$A10 = 64;            } else if (isNaN(iBBhbQgM6)) {                Dtmkv$A10 = 64;            }                RLYPSVZJN3 = RLYPSVZJN3 + this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x63\x68\x61\x72\x41\x74"](XAGERGoCK7) + this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x63\x68\x61\x72\x41\x74"](QwID8) + this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x63\x68\x61\x72\x41\x74"]($W9) + this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x63\x68\x61\x72\x41\x74"](Dtmkv$A10);            }            return RLYPSVZJN3;    },            decode: function(L12) {        var nHwT_sjoe13 = "";        var clKSEmv14, X15, XIQ16;        var kDPXLff17, H18, Wu19, TmR20;        var SGpaM21 = 0;            L12 = L12["\x72\x65\x70\x6c\x61\x63\x65"](/[^A-Za-z0-9\+\/\=]/g, "");            while (SGpaM21 < L12["\x6c\x65\x6e\x67\x74\x68"]) {                kDPXLff17 = this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x69\x6e\x64\x65\x78\x4f\x66"](L12["\x63\x68\x61\x72\x41\x74"](SGpaM21++));            H18 = this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x69\x6e\x64\x65\x78\x4f\x66"](L12["\x63\x68\x61\x72\x41\x74"](SGpaM21++));            Wu19 = this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x69\x6e\x64\x65\x78\x4f\x66"](L12["\x63\x68\x61\x72\x41\x74"](SGpaM21++));            TmR20 = this["\x5f\x6b\x65\x79\x53\x74\x72"]["\x69\x6e\x64\x65\x78\x4f\x66"](L12["\x63\x68\x61\x72\x41\x74"](SGpaM21++));                clKSEmv14 = (kDPXLff17 << 2) | (H18 >> 4);            X15 = ((H18 & 15) << 4) | (Wu19 >> 2);            XIQ16 = ((Wu19 & 3) << 6) | TmR20;                nHwT_sjoe13 = nHwT_sjoe13 + window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](clKSEmv14);                if (Wu19 != 64) {                nHwT_sjoe13 = nHwT_sjoe13 + window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](X15);            }            if (TmR20 != 64) {                nHwT_sjoe13 = nHwT_sjoe13 + window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](XIQ16);            }            }            nHwT_sjoe13 = Dg1["\x5f\x75\x74\x66\x38\x5f\x64\x65\x63\x6f\x64\x65"](nHwT_sjoe13);            return nHwT_sjoe13;        },            _utf8_encode: function(NSUKK22) {        NSUKK22 = NSUKK22["\x72\x65\x70\x6c\x61\x63\x65"](/\r\n/g, "\n");        var $Nq23 = "";            for (var VtsCoAvJA24 = 0; VtsCoAvJA24 < NSUKK22["\x6c\x65\x6e\x67\x74\x68"]; VtsCoAvJA24++) {                var UuQ$M25 = NSUKK22["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](VtsCoAvJA24);                if (UuQ$M25 < 128) {                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](UuQ$M25);            } else if ((UuQ$M25 > 127) && (UuQ$M25 < 2048)) {                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"]((UuQ$M25 >> 6) | 192);                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"]((UuQ$M25 & 63) | 128);            } else {                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"]((UuQ$M25 >> 12) | 224);                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](((UuQ$M25 >> 6) & 63) | 128);                $Nq23 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"]((UuQ$M25 & 63) | 128);            }            }            return $Nq23;    },            _utf8_decode: function(JIZPh26) {        var qhrnJNC27 = "";        var hN28 = 0;        var aE29 = c1 = c2 = 0;            while (hN28 < JIZPh26["\x6c\x65\x6e\x67\x74\x68"]) {                aE29 = JIZPh26["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](hN28);                if (aE29 < 128) {                qhrnJNC27 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](aE29);                hN28++;            } else if ((aE29 > 191) && (aE29 < 224)) {                c2 = JIZPh26["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](hN28 + 1);                qhrnJNC27 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](((aE29 & 31) << 6) | (c2 & 63));                hN28 += 2;            } else {                c2 = JIZPh26["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](hN28 + 1);                c3 = JIZPh26["\x63\x68\x61\x72\x43\x6f\x64\x65\x41\x74"](hN28 + 2);                qhrnJNC27 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](((aE29 & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));                hN28 += 3;            }            }            return qhrnJNC27;    }        }
function getEncryptionCode(){        var RO30 = new window["\x44\x61\x74\x65"]()["\x67\x65\x74\x54\x69\x6d\x65"]()["\x74\x6f\x53\x74\x72\x69\x6e\x67"]();    var vZYllIef$31 = "";    for(var ojQoskcV32=0;ojQoskcV32<RO30["\x6c\x65\x6e\x67\x74\x68"];ojQoskcV32++){        vZYllIef$31 += window["\x53\x74\x72\x69\x6e\x67"]["\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65"](65+window["\x70\x61\x72\x73\x65\x49\x6e\x74"](RO30[ojQoskcV32]));    }    vZYllIef$31 = Dg1["\x65\x6e\x63\x6f\x64\x65"](vZYllIef$31);    return vZYllIef$31;}
