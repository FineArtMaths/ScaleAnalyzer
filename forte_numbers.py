#--------------------------------------------------------------------------------------------
# Functions hastily ported over from https://www.mta.ca/pc-set/calculator/pc_calculate.html#
#--------------------------------------------------------------------------------------------

def get_forte_number(s):
    if s is None:
        return None
    pf = prime_form(s)
    for f in forte_to_pc:
        if pf == forte_to_pc[f]:
            return f
    return None

def normal_form(s):
    if s is None:
        return None
    size = len(s)
    s = sorted(s)
    norm_can = [s]
    size = len(s)
    for i in range(1, size):  
        norm_can.append([])
        for j in range(size):
            if (j == (size - 1)):
                norm_can[i].append(norm_can[i-1][0])
            else:
                norm_can[i].append(norm_can[i-1][j+1])
    winner = norm_can[0]
    for i in range(1, size):
        winner = compare_norm(winner, norm_can[i])
    return winner

def compare_norm(s1, s2):
    size = len(s1)
    is_diff = False
    i = size - 1
    while not is_diff:
        diff1 = mod_12_sub(s1[i], s1[0])
        diff2 = mod_12_sub(s2[i], s2[0])
        if (diff1 == diff2): 
            if (i == 1):
                if (s1[0] < s2[0]):
                    return s1
                else:
                    return s2
            else:
                is_diff = False
        if (diff1 < diff2):
            return s1
        if (diff1 > diff2):
            return s2
        i -= 1
    return 0

def mod_12_sub(pc_a, pc_b):
    if (pc_a < pc_b):
        return ((12 + pc_a) - pc_b)
    else:
        return(pc_a - pc_b)

def prime_form(s):
    s = normal_form(s)
    size = len(s)
    if size == 2:
        prime = [0]
        temp = mod_12_sub(s[1], s[0])
        if (temp > 6):
                prime[1] = 12 - temp
        else:
                prime[1] = temp
        return prime
    else:    
        size = size - 1
        inverse = normInverse(s) 
        choice = compare_norm(s, inverse);
        intervals = []
        for i in range(size):
            intervals.append(mod_12_sub(choice[i + 1], choice[i]))
        intervals.append(mod_12_sub(choice[i], choice[0]))
        return getPrime1(intervals); 

def normInverse(s):
    inverse = []
    for i in range(len(s)):
        inverse.append(12 - s[i])
    return normal_form(inverse)

def getPrime1(intervals):
    prime = [0]
    for i in range(1, len(intervals)):
        prime.append(prime[i - 1] + intervals[i - 1])
    return prime;

#--------------------------------------------------------------------------------------------
# List courtesy of Larry Solomon's website (no longer available online)
# from https://web.archive.org/web/20071208003247/http://solomonsmusic.net/pcsets.htm#Key
#--------------------------------------------------------------------------------------------

"""
    Below we process these in the following ways:
        * Replace PC set lists with actual lists, i.e. "01A" becomes [0, 1, 10]
        * Replace unstarred Forte numbers with the same with A at the end, and remove stars
        * Remove other symbols from the Forte names that we don't need
        * Make a map of Forte number to PC set
    This happens automatically on import (no need to call a function)
"""

forte_raw = [
    ["1","1-1*","0","000000","Unison"],
    ["2","2-1*","01","100000","Semitone"],
    ["3","2-2*","02","010000","Whole-tone"],
    ["4","2-3*","03","001000","Minor Third"],
    ["5","2-4*","04","000100","Major Third"],
    ["6","2-5*","05","000010","Perfect Fourth"],
    ["7","2-6*(6)","06","000001","Tritone"],
    ["8","3-1*","012","210000","BACH /Chromatic Trimirror"],
    ["9","3-2","013","111000","Phrygian Trichord"],
    ["10","3-2B","023","111000","Minor Trichord"],
    ["11","3-3","014","101100","Major-minor Trichord.1"],
    ["12","3-3B","034","101100","Major-minor Trichord.2"],
    ["13","3-4","015","100110","Incomplete Major-seventh Chord.1"],
    ["14","3-4B","045","100110","Incomplete Major-seventh Chord.2"],
    ["15","3-5","016","100011","Rite chord.2, Tritone-fourth.1"],
    ["16","3-5B","056","100011","Rite chord.1, Tritone-fourth.2"],
    ["17","3-6*","024","020100","Whole-tone Trichord"],
    ["18","3-7","025","011010","Incomplete Minor-seventh Chord"],
    ["19","3-7B","035","011010","Incomplete Dominant-seventh Chord.2"],
    ["20","3-8","026","010101","Incomplete Dominant-seventh Chord.1/Italian-sixth"],
    ["21","3-8B","046","010101","Incomplete Half-dim-seventh Chord"],
    ["22","3-9*","027","010020","Quartal Trichord"],
    ["23","3-10*","036","002001","Diminished Chord"],
    ["24","3-11","037","001110","Minor Chord"],
    ["25","3-11B","047","001110","Major Chord"],
    ["26","3-12*(4)","048","000300","Augmented Chord"],
    ["27","4-1*","0123","321000","BACH /Chromatic Tetramirror"],
    ["28","4-2","0124","221100","Major-second Tetracluster.2"],
    ["29","4-2B","0234","221100","Major-second Tetracluster.1"],
    ["30","4-3*","0134","212100","Alternating Tetramirror"],
    ["31","4-4","0125","211110","Minor Third Tetracluster.2"],
    ["32","4-4B","0345","211110","Minor Third Tetracluster.1"],
    ["33","4-5","0126","210111","Major Third Tetracluster.2"],
    ["34","4-5B","0456","210111","Major Third Tetracluster.1"],
    ["35","4-6*","0127","210021","Perfect Fourth Tetramirror"],
    ["36","4-7*","0145","201210","Arabian Tetramirror"],
    ["37","4-8*","0156","200121","Double Fourth Tetramirror"],
    ["38","4-9*(6)","0167","200022","Double Tritone Tetramirror"],
    ["39","4-10*","0235","122010","Minor Tetramirror"],
    ["40","4-11","0135","121110","Phrygian Tetrachord"],
    ["41","4-11B","0245","121110","Major Tetrachord"],
    ["42","4-12<","0236","112101","Harmonic-minor Tetrachord"],
    ["43","4-12B<","0346","112101","Major-third Diminished Tetrachord"],
    ["44","4-13","0136","112011","Minor-second Diminished Tetrachord"],
    ["45","4-13B","0356","112011","Perfect-fourth Diminished Tetrachord"],
    ["46","4-14<","0237","111120","Major-second Minor Tetrachord"],
    ["47","4-14B<","0457","111120","Perfect-fourth Major Tetrachord"],
    ["48","4-Z15..29","0146","111111","All-interval Tetrachord.1"],
    ["49","4-Z15B..29","0256","111111","All-interval Tetrachord.2"],
    ["50","4-16","0157","110121","Minor-second Quartal Tetrachord"],
    ["51","4-16B","0267","110121","Tritone Quartal Tetrachord"],
    ["52","4-17*","0347","102210","Major-minor Tetramirror"],
    ["53","4-18","0147","102111","Major-diminished Tetrachord"],
    ["54","4-18B","0367","102111","Minor-diminished Tetrachord"],
    ["55","4-19","0148","101310","Minor-augmented Tetrachord"],
    ["56","4-19B","0348","101310","Augmented-major Tetrachord"],
    ["57","4-20*","0158","101220","Major-seventh Chord"],
    ["58","4-21*","0246","030201","Whole-tone Tetramirror"],
    ["59","4-22","0247","021120","Major-second Major Tetrachord"],
    ["60","4-22B","0357","021120","Perfect-fourth Minor Tetrachord"],
    ["61","4-23*","0257","021030","Quartal Tetramirror"],
    ["62","4-24*","0248","020301","Augmented Seventh Chord"],
    ["63","4-25*(6)","0268","020202","French-sixth Chord"],
    ["64","4-26*","0358","012120","Minor-seventh Chord"],
    ["65","4-27","0258","012111","Half-diminished Seventh Chord"],
    ["66","4-27B","0368","012111","Dominant-seventh/German-sixth Chord"],
    ["67","4-28*(3)","0369","004002","Diminished-seventh Chord"],
    ["68","4-Z29..15","0137","111111","All-interval Tetrachord.3"],
    ["69","4-Z29B..15","0467","111111","All-interval Tetrachord.4"],
    ["70","5-1*","01234","432100","Chromatic Pentamirror"],
    ["71","5-2","01235","332110","Major-second Pentacluster.2"],
    ["72","5-2B","02345","332110","Major-second Pentacluster.1"],
    ["73","5-3","01245","322210","Minor-second Major Pentachord"],
    ["74","5-3B","01345","322210","Spanish Pentacluster"],
    ["75","5-4","01236","322111","Blues Pentacluster"],
    ["76","5-4B","03456","322111","Minor-third Pentacluster"],
    ["77","5-5","01237","321121","Major-third Pentacluster.2"],
    ["78","5-5B","04567","321121","Major-third Pentacluster.1"],
    ["79","5-6","01256","311221","Oriental Pentacluster.1, Raga Megharanji (13161)"],
    ["80","5-6B","01456","311221","Oriental Pentacluster.2"],
    ["81","5-7","01267","310132","DoublePentacluster.1, Raga Nabhomani (11415)"],
    ["82","5-7B","01567","310132","Double Pentacluster.2"],
    ["83","5-8*","02346","232201","Tritone-Symmetric Pentamirror"],
    ["84","5-9","01246","231211","Tritone-Expanding Pentachord"],
    ["85","5-9B","02456","231211","Tritone-Contracting Pentachord"],
    ["86","5-10","01346","223111","Alternating Pentachord.1"],
    ["87","5-10B","02356","223111","Alternating Pentachord.2"],
    ["88","5-11","02347","222220","Center-cluster Pentachord.1"],
    ["89","5-11B","03457","222220","Center-cluster Pentachord.2"],
    ["90","5-Z12*..36","01356","222121","Locrian Pentamirror"],
    ["91","5-13","01248","221311","Augmented Pentacluster.1"],
    ["92","5-13B","02348","221311","Augmented Pentacluster.2"],
    ["93","5-14","01257","221131","Double-seconds Triple-fourth Pentachord.1"],
    ["94","5-14B","02567","221131","Double-seconds Triple-fourth Pentachord.2"],
    ["95","5-15*","01268","220222","Assymetric Pentamirror"],
    ["96","5-16","01347","213211","Major-minor-dim Pentachord.1"],
    ["97","5-16B","03467","213211","Major-minor-dim Pentachord.2"],
    ["98","5-Z17*..37","01348","212320","Minor-major Ninth Chord"],
    ["99","5-Z18<..38","01457","212221","Gypsy Pentachord.1"],
    ["100","5-Z18B<..38","02367","212221","Gypsy Pentachord.2"],
    ["101","5-19","01367","212122","Javanese Pentachord"],
    ["102","5-19B","01467","212122","Balinese Pentachord"],
    ["103","5-20","01378","211231","Balinese Pelog Pentatonic (12414), Raga Bhupala, Raga Bibhas"],
    ["104","5-20B","01578","211231","Hirajoshi Pentatonic (21414), Iwato (14142), Sakura/Raga Saveri (14214)"],
    ["105","5-21","01458","202420","Syrian Pentatonic/Major-augmented Ninth Chord, Raga Megharanji (13134)"],
    ["106","5-21B","03478","202420","Lebanese Pentachord/Augmented-minor Chord"],
    ["107","5-22*","01478","202321","Persian Pentamirror, Raga reva/Ramkali (13314)"],
    ["108","5-23","02357","132130","Minor Pentachord"],
    ["109","5-23B","02457","132130","Major Pentachord"],
    ["110","5-24","01357","131221","Phrygian Pentachord"],
    ["111","5-24B","02467","131221","Lydian Pentachord"],
    ["112","5-25","02358","123121","Diminished-major Ninth Chord"],
    ["113","5-23B","03568","123121","Minor-diminished Ninth Chord"],
    ["114","5-26<","02458","122311","Diminished-augmented Ninth Chord"],
    ["115","5-26B<","03468","122311","Augmented-diminished Ninth Chord"],
    ["116","5-27","01358","122230","Major-Ninth Chord"],
    ["117","5-27B","03578","122230","Minor-Ninth Chord"],
    ["118","5-28<","02368","122212","Augmented-sixth Pentachord.1"],
    ["119","5-28B<","02568","122212","Augmented-sixth Pentachord.2"],
    ["120","5-29","01368","122131","Kumoi Pentachord.2"],
    ["121","5-29B","02578","122131","Kumoi Pentachord.1"],
    ["122","5-30","01468","121321","Enigmatic Pentachord.1"],
    ["123","5-30B","02478","121321","Enigmatic Pentachord.2, Altered Pentatonic (14223)"],
    ["124","5-31","01369","114112","Diminished Minor-Ninth Chord"],
    ["125","5-31B","02369","114112","Ranjaniraga/Flat-Ninth Pentachord"],
    ["126","5-32","01469","113221","Neapolitan Pentachord.1"],
    ["127","5-32B","01479","113221","Neapolitan Pentachord.2"],
    ["128","5-33*","02468","040402","Whole-tone Pentamirror"],
    ["129","5-34*","02469","032221","Dominant-ninth/major-minor/Prometheus Pentamirror, Dominant Pentatonic (22332)"],
    ["130","5-35*","02479","032140","Black Key Pentatonic/Slendro/Bilahariraga/Quartal Pentamirror, Yo (23232)"],
    ["131","5-Z36..12","01247","222121","Major-seventh Pentacluster.2"],
    ["132","5-Z36B..12","03567","222121","Minor-seventh Pentacluster.1"],
    ["133","5-Z37*..17","03458","212320","Center-cluster Pentamirror"],
    ["134","5-Z38..18","01258","212221","Diminished Pentacluster.1"],
    ["135","5-Z38B..18","03678","212221","Diminished Pentacluster.2"],
    ["136","6-1*","012345","543210","Chromatic Hexamirror/1st ord. all-comb (P6, Ib, RI5)"],
    ["137","6-2","012346","443211","comb I (b)"],
    ["138","6-2B","023456","443211","comb I (1)"],
    ["139","6-Z3..36B","012356","433221",""],
    ["140","6-Z3B..36","013456","433221",""],
    ["141","6-Z4*..37","012456","432321","comb RI (6)"],
    ["142","6-5","012367","422232","comb I (b)"],
    ["143","6-5B","014567","422232","comb I (3)"],
    ["144","6-Z6*..38","012567","421242","Double-cluster Hexamirror"],
    ["145","6-7* (6)","012678","420243","Messiaen's mode 5 (114114), 2nd ord.all-comb(P3+9,I5,Ib,R6,RI2+8)"],
    ["146","6-8*","023457","343230","1st ord.all-comb (P6, I1, RI7)"],
    ["147","6-9","012357","342231","comb I (b)"],
    ["148","6-9B","024567","342231","comb I (3)"],
    ["149","6-Z10..39","013457","333321",""],
    ["150","6-Z10B..39B","023467","333321",""],
    ["151","6-Z11..40B","012457","333231",""],
    ["152","6-Z11B..40","023567","333231",""],
    ["153","6-Z12..41B","012467","332232",""],
    ["154","6-Z12B..41","013567","332232",""],
    ["155","6-Z13*..42","013467","324222","Alternating Hexamirror/comb RI7)"],
    ["156","6-14..14","013458","323430","comb P (6)"],
    ["157","6-14B..14B","034578","323430","comb P (6)"],
    ["158","6-15","012458","323421","comb I (b)"],
    ["159","6-15B","034678","323421","comb I (5)"],
    ["160","6-16","014568","322431","comb I (3)"],
    ["161","6-16B","023478","322431","Megha or Cloud Raga/comb.I (1)"],
    ["162","6-Z17..43B","012478","322332",""],
    ["163","6-Z17B..43","014678","322332",""],
    ["164","6-18","012578","322242","comb I (b)"],
    ["165","6-18B","013678","322242","comb I (5)"],
    ["166","6-Z19..44B","013478","313431",""],
    ["167","6-Z19B..44","014578","313431",""],
    ["168","6-20*(4)","014589","303630","Augmented scale, Genus tertium, 3rd ord. all-comb (P2+6+10, I3+7+b, R4+8, RI1+5+9)"],
    ["169","6-21","023468","242412","comb I (1)"],
    ["170","6-21B","024568","242412","comb I (3)"],
    ["171","6-22","012468","241422","comb I (b)"],
    ["172","6-22B","024678","241422","comb I (5)"],
    ["173","6-Z23*..45","023568","234222","Super-Locrian Hexamirror/comb RI (8)"],
    ["174","6-Z24..46B","013468","233331",""],
    ["175","6-Z24B..46","024578","233331","Melodic-minor Hexachord"],
    ["176","6-Z25..47B","013568","233241","Locrian Hexachord/Suddha Saveriraga"],
    ["177","6-Z25B..47","023578","233241","Minor Hexachord"],
    ["178","6-Z26*..48","013578","232341","Phrygian Hexamirror/comb RI (8)"],
    ["179","6-27","013469","225222","comb I (b)"],
    ["180","6-27B","023569","225222","Pyramid Hexachord/comb I (1)"],
    ["181","6-Z28*..49","013569","224322","Double-Phrygian Hexachord/comb RI (6)"],
    ["182","6-Z29*..50","013689","224232","comb RI (9)"],
    ["183","6-30 (6)","013679","224223","Minor-bitonal Hexachord/comb R (6), I (5,b)"],
    ["184","6-30B (6)","023689","224223","Petrushka chord, Major-bitonal Hexachord, comb R (6), I (1,7)"],
    ["185","6-31","013589","223431","comb I (7)"],
    ["186","6-31B","014689","223431","comb I (b)"],
    ["187","6-32*","024579","143250","Arezzo major Diatonic (221223), major hexamirror, quartal hexamirror, 1st ord.all-comb P (6), I (3), RI (9)"],
    ["188","6-33","023579","143241","Dorian Hexachord/comb I (1)"],
    ["189","6-33B","024679","143241","Dominant-11th/Lydian Hexachord/comb I (5)"],
    ["190","6-34","013579","142422","Scriabin's Mystic Chord or Prometheus Hexachord/comb I (B)"],
    ["191","6-34B","024689","142422","Harmonic Hexachord/Augmented-11th/comb I (7)"],
    ["192","6-35*(2)","02468A","060603","Wholetone scale/6th ord.all-comb.(P+IoddT, R+RIevenT)"],
    ["193","6-Z36..3B","012347","433221",""],
    ["194","6-Z36B..3","034567","433221",""],
    ["195","6-Z37*..4","012348","432321","comb RI (4)"],
    ["196","6-Z38*..6","012378","421242","comb RI (3)"],
    ["197","6-Z39..10","023458","333321",""],
    ["198","6-Z39B..10B","034568","333321",""],
    ["199","6-Z40..11B","012358","333231",""],
    ["200","6-Z40B..11","035678","333231",""],
    ["201","6-Z41..12B","012368","332232",""],
    ["202","6-Z41B..12","025678","332232",""],
    ["203","6-Z42*..13","012369","324222","comb RI (3)"],
    ["204","6-Z43..17B","012568","322332",""],
    ["205","6-Z43B..17","023678","322332",""],
    ["206","6-Z44..19B","012569","313431","Schoenberg Anagram Hexachord"],
    ["207","6-Z44B..19","012589","313431","Bauli raga (133131)"],
    ["208","6-Z45*..23","023469","234222","comb RI (6)"],
    ["209","6-Z46..24B","012469","233331",""],
    ["210","6-Z46B..24","024569","233331",""],
    ["211","6-Z47..25B","012479","233241",""],
    ["212","6-Z47B..25","023479","233241","Blues mode.1 (321132)"],
    ["213","6-Z48*..26","012579","232341","comb RI (2)"],
    ["214","6-Z49*..28","013479","224322","Prometheus Neapolitan mode (132312), comb RI (4)"],
    ["215","6-Z50*..29","014679","224232","comb RI (1)"],
    ["216","7-1*","0123456","654321","Chromatic Heptamirror"],
    ["217","7-2","0123457","554331",""],
    ["218","7-2B","0234567","554331",""],
    ["219","7-3","0123458","544431",""],
    ["220","7-3B","0345678","544431",""],
    ["221","7-4","0123467","544332",""],
    ["222","7-4B","0134567","544332",""],
    ["223","7-5","0123567","543342",""],
    ["224","7-5B","0124567","543342",""],
    ["225","7-6","0123478","533442",""],
    ["226","7-6B","0145678","533442",""],
    ["227","7-7","0123678","532353",""],
    ["228","7-7B","0125678","532353",""],
    ["229","7-8*","0234568","454422",""],
    ["230","7-9","0123468","453432",""],
    ["231","7-9B","0245678","453432",""],
    ["232","7-10","0123469","445332",""],
    ["233","7-10B","0234569","445332",""],
    ["234","7-11","0134568","444441",""],
    ["235","7-11B","0234578","444441",""],
    ["236","7-Z12*..36","0123479","444342",""],
    ["237","7-13","0124568","443532",""],
    ["238","7-13B","0234678","443532",""],
    ["239","7-14","0123578","443352",""],
    ["240","7-14B","0135678","443352",""],
    ["241","7-15*","0124678","442443",""],
    ["242","7-16","0123569","435432",""],
    ["243","7-16B","0134569","435432",""],
    ["244","7-Z17*..37","0124569","434541",""],
    ["245","7-Z18<..38","0123589","434442",""],
    ["246","7-Z18B<..38","0146789","434442",""],
    ["247","7-19","0123679","434343",""],
    ["248","7-19B","0123689","434343",""],
    ["249","7-20","0124789","433452","Chromatic Phrygian inverse (1123113)"],
    ["250","7-20B","0125789","433452","Pantuvarali Raga (1321131), Chromatic Mixolydian (1131132), Chromatic Dorian/Mela Kanakangi (1132113)"],
    ["251","7-21","0124589","424641",""],
    ["252","7-21B","0134589","424641","Gypsy hexatonic (1312113)"],
    ["253","7-22*","0125689","424542","Persian, Major Gypsy, Hungarian Minor, Double Harmonic scale, Bhairav That, Mayamdavagaula Raga (all: 1312131), Oriental (1311312)"],
    ["254","7-23","0234579","354351",""],
    ["255","7-23B","0245679","354351","Tritone Major Heptachord"],
    ["256","7-24","0123579","353442",""],
    ["257","7-24B","0246789","353442","Enigmatic Heptatonic (1322211)"],
    ["258","7-25","0234679","345342",""],
    ["259","7-25B","0235679","345342",""],
    ["260","7-26<","0134579","344532",""],
    ["261","7-26B<","0245689","344532",""],
    ["262","7-27","0124579","344451",""],
    ["263","7-27B","0245789","344451","Modified Blues mode (2121132)"],
    ["264","7-28<","0135679","344433",""],
    ["265","7-28B<","0234689","344433",""],
    ["266","7-29","0124679","344352",""],
    ["267","7-29B","0235789","344352",""],
    ["268","7-30","0124689","343542","Neapolitan-Minor mode (1222131), Mela Dhenuka"],
    ["269","7-30B","0135789","343542",""],
    ["270","7-31","0134679","336333","Alternating Heptachord.1/Hungarian Major mode (3121212)"],
    ["271","7-31B","0235689","336333","Alternating Heptachord.2"],
    ["272","7-32","0134689","335442","Harmonic-Minor mode (2122131), Spanish Gypsy, Mela Kiravani, Pilu That"],
    ["273","7-32B","0135689","335442","Dharmavati Scale (2131221), Harmonic minor inverse (1312212), Mela Cakravana, Raga Ahir Bhairav"],
    ["274","7-33*","012468A","262623","Neapolitan-major mode (1222221)/Leading-Whole-tone mode (222211)"],
    ["275","7-34*","013468A","254442","Harmonic/Super-Locrian, Melodic minor ascending (1212222)/Aug.13th Heptamirror, Jazz Minor"],
    ["276","7-35*","013568A","254361","Major Diatonic Heptachord/Dominant-13th, Locrian (1221222), Phrygian (1222122), Major inverse"],
    ["277","7-Z36..12","0123568","444342",""],
    ["278","7-Z36B..12","0235678","444342",""],
    ["279","7-Z37*..17","0134578","434541",""],
    ["280","7-Z38..18","0124578","434442",""],
    ["281","7-Z38B..18","0134678","434442",""],
    ["282","8-1*","01234567","765442","Chromatic Octamirror"],
    ["283","8-2","01234568","665542",""],
    ["284","8-2B","02345678","665542",""],
    ["285","8-3*","01234569","656542",""],
    ["286","8-4","01234578","655552",""],
    ["287","8-4B","01345678","655552",""],
    ["288","8-5","01234678","654553",""],
    ["289","8-5B","01245678","654553",""],
    ["290","8-6*","01235678","654463",""],
    ["291","8-7*","01234589","645652",""],
    ["292","8-8*","01234789","644563",""],
    ["293","8-9* (6)","01236789","644464","Messiaen's mode 4 (11131113)"],
    ["294","8-10*","02345679","566452",""],
    ["295","8-11","01234579","565552",""],
    ["296","8-11B","02456789","565552",""],
    ["297","8-12<","01345679","556543",""],
    ["298","8-12B<","02345689","556543",""],
    ["299","8-13","01234679","556453",""],
    ["300","8-13B","02356789","556453",""],
    ["301","8-14<","01245679","555562",""],
    ["302","8-14B<","02345789","555562",""],
    ["303","8-Z15..29","01234689","555553",""],
    ["304","8-Z15B..29","01356789","555553",""],
    ["305","8-16","01235789","554563",""],
    ["306","8-16B","01246789","554563",""],
    ["307","8-17*","01345689","546652",""],
    ["308","8-18","01235689","546553",""],
    ["309","8-18B","01346789","546553",""],
    ["310","8-19","01245689","545752",""],
    ["311","8-19B","01345789","545752",""],
    ["312","8-20*","01245789","545662",""],
    ["313","8-21*","0123468A","474643",""],
    ["314","8-22","0123568A","465562",""],
    ["315","8-22B","0123579A","465562","Spanish Octatonic Scale (r9) (12111222)"],
    ["316","8-23*","0123578A","465472","Quartal Octachord, Diatonic Octad"],
    ["317","8-24*","0124568A","464743",""],
    ["318","8-25* (6)","0124678A","464644","Messiaen mode 6 (11221122)"],
    ["319","8-26*","0124579A","456562","Spanish Phrygian (r9) (12112122)/ Blues mode.2 (21211212)"],
    ["320","8-27","0124578A","456553",""],
    ["321","8-27B","0124679A","456553",""],
    ["322","8-28* (3)","0134679A","448444","Alternating Octatonic or Diminished scale (12121212)"],
    ["323","8-Z29..15","01235679","555553",""],
    ["324","8-Z29B..15","02346789","555553",""],
    ["325","9-1*","012345678","876663","Chromatic Nonamirror"],
    ["326","9-2","012345679","777663",""],
    ["327","9-2B","023456789","777663",""],
    ["328","9-3","012345689","767763",""],
    ["329","9-3B","013456789","767763",""],
    ["330","9-4","012345789","766773",""],
    ["331","9-4B","012456789","766773",""],
    ["332","9-5","012346789","766674",""],
    ["333","9-5B","012356789","766674",""],
    ["334","9-6*","01234568A","686763",""],
    ["335","9-7","01234578A","677673","Nonatonic Blues Scale (211111212)"],
    ["336","9-7B","01234579A","677673",""],
    ["337","9-8","01234678A","676764",""],
    ["338","9-8B","01234689A","676764",""],
    ["339","9-9*","01235678A","676683","Raga Ramdasi Malhar (r2) (211122111)"],
    ["340","9-10*","01234679A","668664",""],
    ["341","9-11","01235679A","667773",""],
    ["342","9-11B","01235689A","667773","Diminishing Nonachord"],
    ["343","9-12* (4)","01245689A","666963","Tsjerepnin/Messiaen mode 3 (112112112)"],
    ["344","10-1*","0123456789","988884","Chromatic Decamirror"],
    ["345","10-2*","012345678A","898884",""],
    ["346","10-3*","012345679A","889884",""],
    ["347","10-4*","012345689A","888984",""],
    ["348","10-5*","012345789A","888894","Major-minor mixed (r7)"],
    ["349","10-6* (6)","012346789A","888885","Messiaen mode 7 (1111211112)"],
    ["350","11-1*","0123456789A","AAAAA5","Chromatic Undecamirror"],
    ["351","12-1*(1)","0123456789AB","CCCCC6","Chromatic Scale/Dodecamirror (111111111111)"]
]


forte_to_pc = {}

def process_raw_forte_list():
    global forte_raw
    global forte_to_pc
    for f in forte_raw:
        f_name = f[1]
        if "*" not in f_name and "B" not in f_name:
            f_name = f_name + "A"
        f_name = f_name.replace("*", "")
        f_name = f_name.replace("<", "")
        f_name = f_name.replace(" ", "")
        if "(" in f_name:
            f_name = f_name.split("(")[0]
        if ".." in f_name:
            z_partner = f_name.split("-")[0] + f_name.split("..")[1]
            f_name = f_name.split("..")[0]
        else:
            z_partner = None
        forte_to_pc[f_name] = []
        f_pcs = f[2]
        for i in f_pcs:
            if i == "A":
                i = 10
            elif i == "B":
                i = 11
            else:
                i = int(i)
            forte_to_pc[f_name].append(i)

process_raw_forte_list()
