/**
 * 本代码来自: https://bbs.csdn.net/topics/391839640 评论区，有修改
 */
/*
 * Crypto-JS v2.5.1
 * http://code.google.com/p/crypto-js/
 * (c) 2009-2011 by Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(typeof Crypto=="undefined"||!Crypto.util)&&function(){var e=self.Crypto={},g=e.util={rotl:function(a,b){return a<<b|a>>>32-b},rotr:function(a,b){return a<<32-b|a>>>b},endian:function(a){if(a.constructor==Number)return g.rotl(a,8)&16711935|g.rotl(a,24)&4278255360;for(var b=0;b<a.length;b++)a[b]=g.endian(a[b]);return a},randomBytes:function(a){for(var b=[];a>0;a--)b.push(Math.floor(Math.random()*256));return b},bytesToWords:function(a){for(var b=[],c=0,d=0;c<a.length;c++,d+=8)b[d>>>5]|=a[c]<<24-
    d%32;return b},wordsToBytes:function(a){for(var b=[],c=0;c<a.length*32;c+=8)b.push(a[c>>>5]>>>24-c%32&255);return b},bytesToHex:function(a){for(var b=[],c=0;c<a.length;c++)b.push((a[c]>>>4).toString(16)),b.push((a[c]&15).toString(16));return b.join("")},hexToBytes:function(a){for(var b=[],c=0;c<a.length;c+=2)b.push(parseInt(a.substr(c,2),16));return b},bytesToBase64:function(a){if(typeof btoa=="function")return btoa(f.bytesToString(a));for(var b=[],c=0;c<a.length;c+=3)for(var d=a[c]<<16|a[c+1]<<8|
    a[c+2],e=0;e<4;e++)c*8+e*6<=a.length*8?b.push("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(d>>>6*(3-e)&63)):b.push("=");return b.join("")},base64ToBytes:function(a){if(typeof atob=="function")return f.stringToBytes(atob(a));for(var a=a.replace(/[^A-Z0-9+\/]/ig,""),b=[],c=0,d=0;c<a.length;d=++c%4)d!=0&&b.push(("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(a.charAt(c-1))&Math.pow(2,-2*d+8)-1)<<d*2|"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(a.charAt(c))>>>
    6-d*2);return b}},e=e.charenc={};e.UTF8={stringToBytes:function(a){return f.stringToBytes(unescape(encodeURIComponent(a)))},bytesToString:function(a){return decodeURIComponent(escape(f.bytesToString(a)))}};var f=e.Binary={stringToBytes:function(a){for(var b=[],c=0;c<a.length;c++)b.push(a.charCodeAt(c)&255);return b},bytesToString:function(a){for(var b=[],c=0;c<a.length;c++)b.push(String.fromCharCode(a[c]));return b.join("")}}}();

/*
 * sha1File v1.0.1
 * https://github.com/dwsVad/sha1File
 * (c) 2014 by Protsenko Vadim. All rights reserved.
 * https://github.com/dwsVad/sha1File/blob/master/LICENSE
 */
async function sha1File(settings, progressingCallback)
{
    var promise = new Promise(function (resolve, reject) {
        var hash = [1732584193, -271733879, -1732584194, 271733878, -1009589776];
        var buffer = 1024 * 16 * 64;
        var sha1 = function (block, hash)
        {
            var words = [];
            var count_parts = 16;
            var h0 = hash[0],
                h1 = hash[1],
                h2 = hash[2],
                h3 = hash[3],
                h4 = hash[4];
            for(var i = 0; i < block.length; i += count_parts)
            {
                var th0 = h0,
                    th1 = h1,
                    th2 = h2,
                    th3 = h3,
                    th4 = h4;
                for(var j = 0; j < 80; j++)
                {
                    if(j < count_parts)
                        words[j] = block[i + j] | 0;
                    else
                    {
                        var n = words[j - 3] ^ words[j - 8] ^ words[j - 14] ^ words[j - count_parts];
                        words[j] = (n << 1) | (n >>> 31);
                    }
                    var f,k;
                    if(j < 20)
                    {
                        f = (h1 & h2 | ~h1 & h3);
                        k = 1518500249;
                    }
                    else if(j < 40)
                    {
                        f = (h1 ^ h2 ^ h3);
                        k = 1859775393;
                    }
                    else if(j < 60)
                    {
                        f = (h1 & h2 | h1 & h3 | h2 & h3);
                        k = -1894007588;
                    }
                    else
                    {
                        f = (h1 ^ h2 ^ h3);
                        k = -899497514;
                    }

                    var t = ((h0 << 5) | (h0 >>> 27)) +h4 + (words[j] >>> 0) + f + k;
                    h4 = h3;
                    h3 = h2;
                    h2 = (h1 << 30) | (h1 >>> 2);
                    h1 = h0;
                    h0 = t;
                }
                h0 = (h0 + th0) | 0;
                h1 = (h1 + th1) | 0;
                h2 = (h2 + th2) | 0;
                h3 = (h3 + th3) | 0;
                h4 = (h4 + th4) | 0;
            }
            return [h0, h1, h2, h3, h4];
        }

        var run = function(file,inStart,inEnd)
        {
            var end = Math.min(inEnd, file.size);
            var start = inStart;
            var reader = new FileReader();

            reader.onload = function()
            {
                file.sha1_progress = (end * 100 / file.size);
                var event = event || window.event;
                var result = event.result || event.target.result
                var block = Crypto.util.bytesToWords( new Uint8Array(result));

                if (end === file.size)
                {
                    var bTotal, bLeft, bTotalH, bTotalL;
                    bTotal = file.size * 8;
                    bLeft = (end - start) * 8;

                    bTotalH = Math.floor(bTotal / 0x100000000);
                    bTotalL = bTotal & 0xFFFFFFFF;

                    // Padding
                    block[bLeft >>> 5] |= 0x80 << (24 - bLeft % 32);
                    block[((bLeft + 64 >>> 9) << 4) + 14] = bTotalH;
                    block[((bLeft + 64 >>> 9) << 4) + 15] = bTotalL;

                    hash = sha1(block, hash);
                    file.sha1_hash = Crypto.util.bytesToHex(Crypto.util.wordsToBytes(hash));
                    // console.log(file.sha1_hash);
                    resolve(file.sha1_hash);
                }
                else
                {
                    hash = sha1(block, hash);
                    start += buffer;
                    end += buffer;
                    run(file,start,end);
                }
                progressingCallback(file);
                // console.log(file.sha1_progress);
            }
            var blob = file.slice(start, end);
            reader.readAsArrayBuffer(blob);
        }

        var checkApi = function()
        {
            if((typeof File == 'undefined'))
                return false;

            if (!File.prototype.slice) {
                if(File.prototype.webkitSlice)
                    File.prototype.slice = File.prototype.webkitSlice;
                else if(File.prototype.mozSlice)
                    File.prototype.slice = File.prototype.mozSlice;
            }

            if (!window.File || !window.FileReader || !window.FileList || !window.Blob || !File.prototype.slice)
                return false;

            return true;
        }

        if(checkApi())
        {
            run(settings,0,buffer);
        }
        else
            // return false;
            reject("File API is not supported");
    });
    return await promise;
}