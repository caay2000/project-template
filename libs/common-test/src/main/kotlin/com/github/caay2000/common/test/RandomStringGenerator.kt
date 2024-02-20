package com.github.caay2000.common.test

object RandomStringGenerator {
    fun randomString(): String = "${adjectives.random()} ${colors.random()} ${animals.random()}"

    fun randomName(): String = animals.random()

    fun randomSurname(): String = "${adjectives.random()} ${colors.random()}"

    fun randomEmail(): String = "${adjectives.random()}.${colors.random()}@${animals.random()}.${domains.random()}"

    fun randomNumber(size: Int): String = List(size) { numbers.random() }.joinToString(separator = "")

    fun randomUppercaseLetter(size: Int = 1): String = List(size) { uppercaseLetter.random() }.joinToString(separator = "")

    private val numbers: List<Char> = ('0'..'9').toList()
    private val uppercaseLetter: List<Char> = ('A'..'Z').toList()

    private val adjectives: List<String> =
        listOf(
            "adorable",
            "adventurous",
            "aggressive",
            "agreeable",
            "alert",
            "alive",
            "amused",
            "angry",
            "annoyed",
            "annoying",
            "anxious",
            "arrogant",
            "ashamed",
            "attractive",
            "average",
            "awful",
            "bad",
            "beautiful",
            "better",
            "bewildered",
            "black",
            "bloody",
            "blue",
            "blue.eyed",
            "blushing",
            "bored",
            "brainy",
            "brave",
            "breakable",
            "bright",
            "busy",
            "calm",
            "careful",
            "cautious",
            "charming",
            "cheerful",
            "clean",
            "clear",
            "clever",
            "cloudy",
            "clumsy",
            "colorful",
            "combative",
            "comfortable",
            "concerned",
            "condemned",
            "confused",
            "cooperative",
            "courageous",
            "crazy",
            "creepy",
            "crowded",
            "cruel",
            "curious",
            "cute",
            "dangerous",
            "dark",
            "dead",
            "defeated",
            "defiant",
            "delightful",
            "depressed",
            "determined",
            "different",
            "difficult",
            "disgusted",
            "distinct",
            "disturbed",
            "dizzy",
            "doubtful",
            "drab",
            "dull",
            "eager",
            "easy",
            "elated",
            "elegant",
            "embarrassed",
            "enchanting",
            "encouraging",
            "energetic",
            "enthusiastic",
            "envious",
            "evil",
            "excited",
            "expensive",
            "exuberant",
            "fair",
            "faithful",
            "famous",
            "fancy",
            "fantastic",
            "fierce",
            "filthy",
            "fine",
            "foolish",
            "fragile",
            "frail",
            "frantic",
            "friendly",
            "frightened",
            "funny",
            "gentle",
            "gifted",
            "glamorous",
            "gleaming",
            "glorious",
            "good",
            "gorgeous",
            "graceful",
            "grieving",
            "grotesque",
            "grumpy",
            "handsome",
            "happy",
            "healthy",
            "helpful",
            "helpless",
            "hilarious",
            "homeless",
            "homely",
            "horrible",
            "hungry",
            "hurt",
            "ill",
            "important",
            "impossible",
            "inexpensive",
            "innocent",
            "inquisitive",
            "itchy",
            "jealous",
            "jittery",
            "jolly",
            "joyous",
            "kind",
            "lazy",
            "light",
            "lively",
            "lonely",
            "long",
            "lovely",
            "lucky",
            "magnificent",
            "misty",
            "modern",
            "motionless",
            "muddy",
            "mushy",
            "mysterious",
            "nasty",
            "naughty",
            "nervous",
            "nice",
            "nutty",
            "obedient",
            "obnoxious",
            "odd",
            "old-fashioned",
            "open",
            "outrageous",
            "outstanding",
            "panicky",
            "perfect",
            "plain",
            "pleasant",
            "poised",
            "poor",
            "powerful",
            "precious",
            "prickly",
            "proud",
            "putrid",
            "puzzled",
            "quaint",
            "real",
            "relieved",
            "repulsive",
            "rich",
            "scary",
            "selfish",
            "shiny",
            "shy",
            "silly",
            "sleepy",
            "smiling",
            "smoggy",
            "sore",
            "sparkling",
            "splendid",
            "spotless",
            "stormy",
            "strange",
            "stupid",
            "successful",
            "super",
            "talented",
            "tame",
            "tasty",
            "tender",
            "tense",
            "terrible",
            "thankful",
            "thoughtful",
            "thoughtless",
            "tired",
            "tough",
            "troubled",
            "ugliest",
            "ugly",
            "uninterested",
            "unsightly",
            "unusual",
            "upset",
            "uptight",
            "vast",
            "victorious",
            "vivacious",
            "wandering",
            "weary",
            "wicked",
            "wide.eyed",
            "wild",
            "witty",
            "worried",
            "worrisome",
            "wrong",
            "zany",
            "zealous",
        )

    private val animals: List<String> =
        listOf(
            "aardvark",
            "abyssinian",
            "adelie.penguin",
            "affenpinscher",
            "afghan.hound",
            "african.bush.elephant",
            "african.civet",
            "african.clawed.frog",
            "african.forest.elephant",
            "african.palm.civet",
            "african.penguin",
            "african.tree.toad",
            "african.wild.dog",
            "ainu.dog",
            "airedale.terrier",
            "akbash",
            "akita",
            "alaskan.malamute",
            "albatross",
            "aldabra.giant.tortoise",
            "alligator",
            "alpaca",
            "alpine.dachsbracke",
            "amazon.river.dolphin",
            "american.alsatian",
            "american.bulldog",
            "american.cocker.spaniel",
            "american.coonhound",
            "american.eskimo.dog",
            "american.foxhound",
            "american.pit.bull.terrier",
            "american.staffordshire.terrier",
            "american.water.spaniel",
            "amur.leopard",
            "anatolian.shepherd.dog",
            "anchovies",
            "angelfish",
            "ant",
            "anteater",
            "antelope",
            "appenzeller.dog",
            "arapaima",
            "arctic.fox",
            "arctic.hare",
            "arctic.wolf",
            "armadillo",
            "asian.elephant",
            "asian.giant.hornet",
            "asian.palm.civet",
            "asiatic.black.bear",
            "aurochs",
            "australian.cattle.dog",
            "australian.kelpie.dog",
            "australian.mist",
            "australian.shepherd",
            "australian.terrier",
            "avocet",
            "axolotl",
            "aye.aye",
            "baboon",
            "bactrian.camel",
            "badger",
            "baiji",
            "balinese",
            "banded.palm.civet",
            "bandicoot",
            "barb",
            "barn.owl",
            "barnacle",
            "barracuda",
            "barramundi.fish",
            "basenji.dog",
            "basking.shark",
            "basset.fauve.de.bretagne",
            "basset.hound",
            "bat",
            "bavarian.mountain.hound",
            "beagle",
            "bear",
            "bearded.collie",
            "bearded.dragon",
            "beaver",
            "bedlington.terrier",
            "beetle",
            "beluga.sturgeon",
            "bengal.tiger",
            "bernese.mountain.dog",
            "bichir",
            "bichon.frise",
            "biewer.terrier",
            "binturong",
            "bird",
            "birds.of.paradise",
            "birman",
            "bison",
            "black.marlin",
            "black.rhinoceros",
            "black.russian.terrier",
            "black.widow.spider",
            "blobfish",
            "bloodhound",
            "blue.jay",
            "blue.lacy.dog",
            "blue.whale",
            "bluetick.coonhound",
            "bobcat",
            "bolognese.dog",
            "bombay",
            "bongo",
            "bonito.fish",
            "bonobo",
            "booby",
            "border.collie",
            "border.terrier",
            "bornean.orangutan",
            "borneo.elephant",
            "bottlenose.dolphin",
            "bowfin",
            "bowhead.whale",
            "boxer.dog",
            "boykin.spaniel",
            "brazilian.terrier",
            "british.timber",
            "brown.bear",
            "budgerigar",
            "buffalo",
            "bull.shark",
            "bull.terrier",
            "bulldog",
            "bullfrog",
            "bullmastiff",
            "bumblebee",
            "burmese",
            "burrowing.frog",
            "butterfly",
            "butterfly.fish",
            "caiman",
            "caiman.lizard",
            "cairn.terrier",
            "camel",
            "camel.spider",
            "canaan.dog",
            "canadian.eskimo.dog",
            "capybara",
            "caracal",
            "carolina.dog",
            "carp",
            "cassowary",
            "cat",
            "caterpillar",
            "catfish",
            "cavalier.king.charles.spaniel",
            "centipede",
            "cesky.fousek",
            "chameleon",
            "chamois",
            "cheetah",
            "chesapeake.bay.retriever",
            "chicken",
            "chihuahua",
            "chimaera",
            "chimpanzee",
            "chinchilla",
            "chinese.crested.dog",
            "chinook",
            "chinstrap.penguin",
            "chipmunk",
            "chow.chow",
            "cichlid",
            "clouded.leopard",
            "clownfish",
            "clumber.spaniel",
            "coati",
            "cockatoo",
            "cockroach",
            "coelacanth",
            "collared.peccary",
            "collie",
            "colossal.squid",
            "common.buzzard",
            "common.frog",
            "common.loon",
            "common.toad",
            "coopers.hawk",
            "coral",
            "cotton.top.tamarin",
            "cougar",
            "cow",
            "coyote",
            "crab",
            "crab.eating.macaque",
            "crane",
            "crested.penguin",
            "crocodile",
            "cross.river.gorilla",
            "curly.coated.retriever",
            "cuscus",
            "cuttlefish",
            "dachshund",
            "dalmatian",
            "darwins.frog",
            "deer",
            "desert.tortoise",
            "deutsche.bracke",
            "dhole",
            "dingo",
            "discus",
            "doberman.pinscher",
            "dodo",
            "dog",
            "dogo.argentino",
            "dogue.de.bordeaux",
            "dolphin",
            "donkey",
            "dormouse",
            "dragonfish",
            "dragonfly",
            "drever",
            "drum.fish",
            "duck",
            "dugong",
            "dunker",
            "dusky.dolphin",
            "dwarf.crocodile",
            "eagle",
            "earwig",
            "eastern.gorilla",
            "eastern.lowland.gorilla",
            "echidna",
            "edible.frog",
            "eel",
            "egyptian.mau",
            "electric.eel",
            "elephant",
            "elephant.seal",
            "elephant.shrew",
            "emperor.penguin",
            "emperor.tamarin",
            "emu",
            "english.cocker.spaniel",
            "english.shepherd",
            "english.springer.spaniel",
            "entlebucher.mountain.dog",
            "epagneul.pont.audemer",
            "ermine",
            "eskimo.dog",
            "estrela.mountain.dog",
            "falcon",
            "false.killer.whale",
            "fangtooth",
            "fennec.fox",
            "ferret",
            "field.spaniel",
            "fin.whale",
            "finnish.spitz",
            "fire.bellied.toad",
            "fish",
            "fishing.cat",
            "flamingo",
            "flat.coat.retriever",
            "flounder",
            "fluke.fish",
            "fly",
            "flying.squirrel",
            "fossa",
            "fox",
            "fox.terrier",
            "french.bulldog",
            "frigatebird",
            "frilled.lizard",
            "frilled.shark",
            "frog",
            "fur.seal",
            "galapagos.penguin",
            "galapagos.tortoise",
            "gar",
            "gecko",
            "gentoo.penguin",
            "geoffroys.tamarin",
            "gerbil",
            "german.pinscher",
            "german.shepherd.guide",
            "gharial",
            "giant.african.land.snail",
            "giant.clam",
            "giant.panda.bear",
            "giant.schnauzer",
            "gibbon",
            "gila.monster",
            "giraffe",
            "glass.lizard",
            "glow.worm",
            "goat",
            "goblin.shark",
            "golden.lion.tamarin",
            "golden.masked.owl",
            "golden.oriole",
            "golden.retriever.complete.pet.guide",
            "golden.crowned.flying.fox",
            "goose",
            "gopher",
            "gorilla",
            "grasshopper",
            "great.dane",
            "great.pyrenees",
            "great.white.shark",
            "greater.swiss.mountain.dog",
            "green.anole",
            "green.bee.eater",
            "greenland.dog",
            "grey.mouse.lemur",
            "grey.reef.shark",
            "grey.seal",
            "greyhound",
            "grizzly.bear",
            "grouse",
            "guinea.fowl",
            "guinea.pig",
            "guppy",
            "hagfish",
            "hammerhead.shark",
            "hamster",
            "hare",
            "harpy.eagle",
            "harrier",
            "havanese",
            "hawaiian.crow",
            "hedgehog",
            "hercules.beetle",
            "hermit.crab",
            "heron",
            "herring",
            "highland.cattle",
            "himalayan",
            "hippopotamus",
            "honey.badger",
            "honey.bee",
            "hoopoe",
            "horn.shark",
            "hornbill",
            "horned.frog",
            "horse",
            "horseshoe.crab",
            "howler.monkey",
            "human",
            "humboldt.penguin",
            "hummingbird",
            "humpback.whale",
            "hyena",
            "ibis",
            "ibizan.hound",
            "iguana",
            "immortal.jellyfish",
            "impala",
            "indian.elephant",
            "indian.palm.squirrel",
            "indian.rhinoceros",
            "indian.star.tortoise",
            "indochinese.tiger",
            "indri",
            "insects",
            "irish.setter",
            "irish.wolfhound",
            "italian.greyhound",
            "jack.russel",
            "jackal",
            "jaguar",
            "japanese.chin",
            "japanese.macaque",
            "javan.rhinoceros",
            "javanese",
            "jellyfish",
            "jerboa",
            "kakapo",
            "kangaroo",
            "keel.billed.toucan",
            "keeshond",
            "killer.whale",
            "king.cobra",
            "king.crab",
            "king.penguin",
            "kingfisher",
            "kiwi",
            "koala",
            "komodo.dragon",
            "krill",
            "kudu",
            "labradoodle",
            "labrador.retriever",
            "ladybug",
            "lamprey",
            "leaf.tailed.gecko",
            "lemming",
            "lemur",
            "leopard",
            "leopard.cat",
            "leopard.seal",
            "leopard.tortoise",
            "liger",
            "lion",
            "lionfish",
            "little.penguin",
            "lizard",
            "llama",
            "lobster",
            "long.eared.owl",
            "lungfish",
            "lynx",
            "macaroni.penguin",
            "macaw",
            "magellanic.penguin",
            "magpie",
            "maine.coon",
            "malayan.civet",
            "malayan.tiger",
            "maltese",
            "mammals",
            "manatee",
            "mandrill",
            "maned.wolf",
            "manta.ray",
            "marine.toad",
            "markhor",
            "marmot",
            "marsh.frog",
            "masked.palm.civet",
            "mastiff",
            "mayfly",
            "meerkat",
            "megalodon",
            "mexican.free.tailed.bat",
            "milkfish",
            "millipede",
            "mink",
            "minke.whale",
            "mole",
            "molly",
            "monarch.butterfly",
            "mongoose",
            "mongrel",
            "monitor.lizard",
            "monkey",
            "monkfish",
            "monte.iberia.eleuth",
            "moorhen",
            "moose",
            "moray.eel",
            "moth",
            "mountain.gorilla",
            "mountain.lion",
            "mouse",
            "mule",
            "muskrat",
            "narwhal",
            "neanderthal",
            "neapolitan.mastiff",
            "newfoundland",
            "newt",
            "nightingale",
            "norfolk.terrier",
            "north.american.black.bear",
            "northern.inuit.dog",
            "norwegian.forest",
            "numbat",
            "nurse.shark",
            "ocelot",
            "octopus",
            "okapi",
            "old.english.sheepdog",
            "olm",
            "opossum",
            "orangutan",
            "ostrich",
            "otter",
            "oyster",
            "paddlefish",
            "pademelon",
            "pangolin",
            "panther",
            "parrot",
            "patas.monkey",
            "peacock",
            "pekingese",
            "pelican",
            "penguin",
            "pere.davids.deer",
            "peregrine.falcon",
            "persian",
            "petit.basset.griffon.vendéen",
            "pheasant",
            "pied.tamarin",
            "pig",
            "pika",
            "pike.fish",
            "pink.fairy.armadillo",
            "piranha",
            "platypus",
            "pointer",
            "poison.dart.frog",
            "polar.bear",
            "pond.skater",
            "poodle",
            "pool.frog",
            "porcupine",
            "porpoise",
            "possum",
            "prawn",
            "proboscis.monkey",
            "pufferfish",
            "puffin",
            "pug",
            "puma",
            "purple.emperor.butterfly",
            "puss.moth",
            "pygmy.hippopotamus",
            "pygmy.marmoset",
            "quail",
            "quetzal",
            "quokka",
            "quoll",
            "rabbit",
            "raccoon",
            "raccoon.dog",
            "radiated.tortoise",
            "ragdoll",
            "rat",
            "rattlesnake",
            "red.finch",
            "red.fox",
            "red.knee.tarantula",
            "red.panda",
            "red.wolf",
            "red.handed.tamarin",
            "reindeer",
            "rhinoceros",
            "river.turtle",
            "robin",
            "rock.hyrax",
            "rockfish",
            "rockhopper.penguin",
            "roseate.spoonbill",
            "rottweiler",
            "royal.penguin",
            "russian.blue",
            "saarloos.wolfdog",
            "saber.toothed.tiger",
            "sable",
            "saiga",
            "saint.bernard",
            "salamander",
            "salmon",
            "samoyed",
            "sand.lizard",
            "saola",
            "sardines",
            "sawfish",
            "scimitar.horned.oryx",
            "scorpion",
            "scorpion.fish",
            "sea.dragon",
            "sea.lion",
            "sea.otter",
            "sea.slug",
            "sea.squirt",
            "sea.turtle",
            "sea.urchin",
            "seahorse",
            "seal",
            "serval",
            "shark",
            "sheep",
            "shiba.inu",
            "shih.tzu",
            "shrimp",
            "siamese",
            "siamese.fighting.fish",
            "siberian",
            "siberian.husky",
            "siberian.tiger",
            "silver.dollar",
            "skate.fish",
            "skunk",
            "sloth",
            "slow.worm",
            "snail",
            "snake",
            "snapping.turtle",
            "snowshoe",
            "snowy.owl",
            "somali",
            "south.china.tiger",
            "spadefoot.toad",
            "sparrow",
            "spectacled.bear",
            "sperm.whale",
            "spider.monkey",
            "spiny.dogfish",
            "spixs.macaw",
            "sponge",
            "squid",
            "squirrel",
            "squirrel.monkey",
            "sri.lankan.elephant",
            "staffordshire.bull.terrier",
            "stag.beetle",
            "starfish",
            "stellers.sea.cow",
            "stick.insect",
            "stingray",
            "stoat",
            "striped.rocket.frog",
            "sturgeon",
            "sucker.fish",
            "sugar.glider",
            "sumatran.elephant",
            "sumatran.orangutan",
            "sumatran.rhinoceros",
            "sumatran.tiger",
            "sun.bear",
            "swai.fish",
            "swan",
            "swedish.vallhund",
            "tamaskan",
            "tang",
            "tapanuli.orangutan",
            "tapir",
            "tarpon",
            "tarsier",
            "tasmanian.devil",
            "tawny.owl",
            "teddy.roosevelt.terrier",
            "termite",
            "tetra",
            "thorny.devil",
            "tibetan.mastiff",
            "tiffany",
            "tiger",
            "tiger.salamander",
            "tiger.shark",
            "tortoise",
            "toucan",
            "tree.frog",
            "tropicbird",
            "tuatara",
            "turkey",
            "turkish.angora",
            "uakari",
            "uguisu",
            "umbrellabird",
            "utonagan",
            "vampire.bat",
            "vampire.squid",
            "vaquita",
            "vervet.monkey",
            "vulture",
            "wallaby",
            "walleye.fish",
            "walrus",
            "wandering.albatross",
            "warthog",
            "wasp",
            "water.buffalo",
            "water.dragon",
            "water.vole",
            "weasel",
            "west.highland.terrier",
            "western.gorilla",
            "western.lowland.gorilla",
            "whale.shark",
            "whippet",
            "white.rhinoceros",
            "white.tiger",
            "white.faced.capuchin",
            "wild.boar",
            "wildebeest",
            "wolf",
            "wolf.eel",
            "wolf.spider",
            "wolffish",
            "wolverine",
            "wombat",
            "woodlouse",
            "woodpecker",
            "woolly.mammoth",
            "woolly.monkey",
            "wrasse",
            "wyoming.toad",
            "x.ray.tetra",
            "xerus",
            "yak",
            "yellow.eyed.penguin",
            "yorkshire.terrier",
            "zebra",
            "zebra.shark",
            "zebu",
            "zonkey",
            "zorse",
        )

    private val colors: List<String> =
        listOf(
            "alice.blue",
            "antique.white",
            "aqua",
            "aquamarine",
            "azure",
            "beige",
            "bisque",
            "black",
            "blanched.almond",
            "blue",
            "blue.violet",
            "brown",
            "burly.wood",
            "cadet.blue",
            "chartreuse",
            "chocolate",
            "coral",
            "cornflower.blue",
            "cornsilk",
            "crimson",
            "cyan",
            "dark.blue",
            "dark.cyan",
            "dark.golden.rod",
            "dark.gray",
            "dark.grey",
            "dark.green",
            "dark.khaki",
            "dark.magenta",
            "dark.olive.green",
            "dark.orange",
            "dark.orchid",
            "dark.red",
            "dark.salmon",
            "dark.sea.green",
            "dark.slate.blue",
            "dark.slate.gray",
            "dark.slate.grey",
            "dark.turquoise",
            "dark.violet",
            "deep.pink",
            "deep.sky.blue",
            "dim.gray",
            "dim.grey",
            "dodger.blue",
            "fire.brick",
            "floral.white",
            "forest.green",
            "fuchsia",
            "gainsboro",
            "ghost.white",
            "gold",
            "golden.rod",
            "gray",
            "grey",
            "green",
            "green.yellow",
            "honey.dew",
            "hot.pink",
            "indian.red",
            "indigo",
            "ivory",
            "khaki",
            "lavender",
            "lavender.blush",
            "lawn.green",
            "lemon.chiffon",
            "light.blue",
            "light.coral",
            "light.cyan",
            "light.golden.rod.yellow",
            "light.gray",
            "light.grey",
            "light.green",
            "light.pink",
            "light.salmon",
            "light.sea.green",
            "light.sky.blue",
            "light.slate.gray",
            "light.slate.grey",
            "light.steel.blue",
            "light.yellow",
            "lime",
            "lime.green",
            "linen",
            "magenta",
            "maroon",
            "medium.aqua.marine",
            "medium.blue",
            "medium.orchid",
            "medium.purple",
            "medium.sea.green",
            "medium.slate.blue",
            "medium.spring.green",
            "medium.turquoise",
            "medium.violet.red",
            "midnight.blue",
            "mint.cream",
            "misty.rose",
            "moccasin",
            "navajo.white",
            "navy",
            "old.lace",
            "olive",
            "olive.drab",
            "orange",
            "orange.red",
            "orchid",
            "pale.golden.rod",
            "pale.green",
            "pale.turquoise",
            "pale.violet.red",
            "papaya.whip",
            "peach.puff",
            "peru",
            "pink",
            "plum",
            "powder.blue",
            "purple",
            "rebecca.purple",
            "red",
            "rosy.brown",
            "royal.blue",
            "saddle.brown",
            "salmon",
            "sandy.brown",
            "sea.green",
            "sea.shell",
            "sienna",
            "silver",
            "sky.blue",
            "slate.blue",
            "slate.gray",
            "slate.grey",
            "snow",
            "spring.green",
            "steel.blue",
            "tan",
            "teal",
            "thistle",
            "tomato",
            "turquoise",
            "violet",
            "wheat",
            "white",
            "white.smoke",
            "yellow",
            "yellow.green",
        )

    private val domains: List<String> =
        listOf(
            "com",
            "net",
            "io",
            "co.uk",
            "es",
            "cat",
        )
}
