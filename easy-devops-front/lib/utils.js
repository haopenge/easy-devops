/**
 * 判断值是否非空
 */
export function isEmptyStr(val) {
  return typeof (val) === 'undefined' || val == null || val === ''
}

/**
 * 获取剪切板内容 返回promise
 */
export function getClipboardText() {
  return navigator.clipboard.readText()
}

/**
 * ssh命令
 */
export function getSshCommand(username, host, port) {
  return `ssh -p ${port} ${username}@${host}`
}

/**
 * 格式化秒
 */
export function formatSecond(s, p = 'HH:mm') {
  return dateFormat(new Date(~~s * 1000), p)
}

/**
 * 10进制权限 转 字符串权限
 */
export function permission10toString(permission) {
  const ps = (permission + '')
  let res = ''
  for (let i = 0; i < ps.length; i++) {
    const per = ps.charAt(i)
    if ((per & 4) === 0) {
      res += '-'
    } else {
      res += 'r'
    }
    if ((per & 2) === 0) {
      res += '-'
    } else {
      res += 'w'
    }
    if ((per & 1) === 0) {
      res += '-'
    } else {
      res += 'x'
    }
  }
  return res
}

/**
 * 返回base64实际数据
 */
export function getBase64Data(e) {
  return e.substring(e.indexOf(',') + 1)
}

/**
 *读取文件base64 返回promise
 */
export function readFileBase64(e) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(e)
    reader.onload = res => {
      resolve(res.target.result)
    }
    reader.onerror = err => {
      reject(err)
    }
  })
}

/**
 * 获取页面路由
 */
export function getRoute(url = location.href) {
  return url.substring(url.lastIndexOf('#') + 1).split('?')[0]
}

/**
 * 格式化数字为 ,分割
 */
export function formatNumber(value = 0) {
  value += ''
  const list = value.split('.')
  const prefix = list[0].charAt(0) === '-' ? '-' : ''
  let num = prefix ? list[0].slice(1) : list[0]
  let result = ''
  while (num.length > 3) {
    result = `,${num.slice(-3)}${result}`
    num = num.slice(0, num.length - 3)
  }
  if (num) {
    result = num + result
  }
  return `${prefix}${result}${list[1] ? `.${list[1]}` : ''}`
}

/**
 * 判断是否为数字
 */
export function isNumber(value, decimal = true, negative = true) {
  let reg
  if (decimal && negative) {
    reg = /^-?[0-9]*(\.[0-9]*)?$/
  } else if (!decimal && negative) {
    reg = /^-?[0-9]*$/
  } else if (decimal && !negative) {
    reg = /^[0-9]*(\.[0-9]*)?$/
  } else if (!decimal && !negative) {
    reg = /^[0-9]*$/
  } else {
    return false
  }
  return (!isNaN(value) && reg.test(value)) || value === ''
}

/**
 * 替换数字
 */
export function replaceNumber(value) {
  const s = value.charAt(value.length - 1)
  if (s === '.' || s === '-') {
    return s.substring(0, value.length - 1)
  }
  return value
}

/**
 * 给array的元素定义key
 */
export function defineArrayKey(array, key, value = null) {
  if (!array || !array.length) {
    return
  }
  for (const item of array) {
    item[key] = value
  }
}

/**
 * 给array的元素定义key
 */
export function defineArrayKeys(array) {
  if (!array || !array.length) {
    return
  }
  for (const item of array) {
    for (let i = 1; i < arguments.length; i++) {
      item[arguments[i]] = null
    }
  }
}

/**
 * 获取解析路径
 */
export function getPathAnalysis(analysisPath, paths = []) {
  const lastSymbol = analysisPath.lastIndexOf('/')
  if (lastSymbol === -1) {
    paths.unshift({
      name: '/',
      path: '/'
    })
    return paths
  }
  const name = analysisPath.substring(lastSymbol, analysisPath.length)
  if (!isEmptyStr(name) && name !== '/') {
    paths.unshift({
      name: name.substring(1, name.length),
      path: analysisPath
    })
  }
  return getPathAnalysis(analysisPath.substring(0, lastSymbol), paths)
}

/**
 * 替换路径
 */
export function getPath(path) {
  return path.replace(new RegExp('\\\\+', 'g'), '/')
    .replace(new RegExp('/+', 'g'), '/')
}

/**
 * 获取父级路径
 */
export function getParentPath(path) {
  const paths = getPath(path).split('/')
  const len = paths.length
  if (len <= 2) {
    return '/'
  }
  let parent = ''
  for (let i = 0; i < len - 1; i++) {
    parent += paths[i]
    if (i !== len - 2) {
      parent += '/'
    }
  }
  return parent
}

/**
 * 清除xss
 */
export function cleanXss(s) {
  s = s.replaceAll('&', '&amp;')
  s = s.replaceAll('<', '&lt;')
  s = s.replaceAll('>', '&gt;')
  s = s.replaceAll('\'', '&apos;')
  s = s.replaceAll('"', '&quot;')
  s = s.replaceAll('\n', '<br/>')
  s = s.replaceAll('\t', '&nbsp;&nbsp;&nbsp;&nbsp;')
  return s
}

/**
 * 替换高亮关键字
 */
export function replaceStainKeywords(message) {
  return cleanXss(message)
    .replaceAll('&lt;sb 0&gt;', '<span class="span-blue mx0">')
    .replaceAll('&lt;sb 2&gt;', '<span class="span-blue mx2">')
    .replaceAll('&lt;sb&gt;', '<span class="span-blue mx4">')
    .replaceAll('&lt;/sb&gt;', '</span>')
    .replaceAll('&lt;sr 0&gt;', '<span class="span-red mx0">')
    .replaceAll('&lt;sr 2&gt;', '<span class="span-red mx2">')
    .replaceAll('&lt;sr&gt;', '<span class="span-red mx4">')
    .replaceAll('&lt;/sr&gt;', '</span>')
    .replaceAll('&lt;b&gt;', '<b>')
    .replaceAll('&lt;/b&gt;', '</b>')
}

/**
 * 清除高亮关键字
 */
export function clearStainKeywords(message) {
  return cleanXss(message)
    .replaceAll('&lt;sb 0&gt;', '')
    .replaceAll('&lt;sb 2&gt;', '')
    .replaceAll('&lt;sb&gt;', '')
    .replaceAll('&lt;/sb&gt;', '')
    .replaceAll('&lt;sr 0&gt;', '')
    .replaceAll('&lt;sr 2&gt;', '')
    .replaceAll('&lt;sr&gt;', '')
    .replaceAll('&lt;/sr&gt;', '')
    .replaceAll('&lt;b&gt;', '')
    .replaceAll('&lt;/b&gt;', '')
    .replaceAll('<br/>', '\n')
}

/**
 * 获取唯一的 UUID
 */
export function getUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0
    const v = c === 'x' ? r : (r & 0x3 | 0x8)
    return v.toString(16)
  })
}

/**
 * 下载文件
 */
export function downloadFile(res, fileName) {
  const blob = new Blob([res.data])
  const tempLink = document.createElement('a')
  const blobURL = window.URL.createObjectURL(blob)
  tempLink.style.display = 'none'
  tempLink.href = blobURL
  if (!fileName) {
    fileName = res.headers['content-disposition']
      ? res.headers['content-disposition'].split(';')[1].split('=')[1]
      : new Date().getTime()
  }
  tempLink.download = decodeURIComponent(fileName)
  if (typeof tempLink.download === 'undefined') {
    tempLink.target = '_blank'
  }
  document.body.appendChild(tempLink)
  tempLink.click()
  document.body.removeChild(tempLink)
  window.URL.revokeObjectURL(blobURL)
}

/**
 * 将消息转为分段消息
 */
export function messageToPartialMessages(msg) {
  const max = 4096
  const len = msg.length
  if (len <= max) {
    return [msg]
  }
  let c = ~~(len / max)
  const mod = len % max
  if (mod !== 0) {
    c++
  }
  const arr = []
  for (let i = 0; i < c; i++) {
    if (i === c - 1) {
      arr.push(msg.substr(i * max, mod === 0 ? max : mod))
    } else {
      arr.push(msg.substr(i * max, max))
    }
  }
  return arr
}
