package back;

public class Pattern {

    private boolean matched = false;
    private char[] pattern;
    private int plen;   //正则表达式长度

    public Pattern(char[] pattern){
        this.pattern = pattern;
        this.plen = pattern.length;
    }

    public boolean match(char[] text, int tlen){
        matched = false;
        rematch(0, 0, text, tlen);
        return matched;
    }

    /**
     * @param ti 当前文字下标
     * @param pj 当前正则表达式下标
     * @param text 匹配文本内容
     * @param tlen 文本长度
     */
    public void rematch(int ti, int pj, char[] text, int tlen){
        if(matched) return;
        if(pj == plen){
            if(ti == tlen) matched = true;
            return;
        }
        if(pattern[pj] == '*'){     //匹配任意多个字符
            for(int k = 0; k<= tlen - ti; k++){
                rematch(ti + k, pj+1, text, tlen);
            }
        }else if(pattern[pj] == '?'){   //匹配一个字符或多个字符
            rematch(ti, pj+1, text, tlen);
            rematch(ti + 1, pj+1, text, tlen);
        }else if (ti < tlen && pattern[pj] == text[ti]) { // 纯字符匹配才行
            rematch(ti+1, pj+1, text, tlen);
        }
    }

}
