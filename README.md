# BattleMusic

Ce plugin Spigot permet de jouer de la musique lorsque les joueurs entrent en combat avec des monstres, similairement à la mécanique de combat dans le jeu Skyrim.

## Fonctionnalités

- Démarre automatiquement la lecture de la musique lorsque le joueur entre en combat avec un monstre.
- Arrête la musique lorsque le combat se termine.
- Permet aux administrateurs de configurer la piste audio à jouer et d'autres paramètres.

## Installation

1. Assurez-vous d'avoir un serveur Spigot fonctionnel.
2. Téléchargez le fichier JAR du plugin depuis [la page spigot de ce projet.](https://www.spigotmc.org/resources/battlemusic-1-12-1-18.73435/).
3. Placez le fichier JAR dans le dossier `plugins` de votre serveur.
4. Redémarrez ou rechargez votre serveur Spigot.

## Configuration

Après avoir installé le plugin, vous pouvez personnaliser son comportement en modifiant le fichier de configuration `config.yml` situé dans le dossier `plugins/BattleMusic`.

Exemple de configuration :

```yaml
ignore-creative: true # Can creative player start fight music
run-away-time: 15 # The time after the music cancel when they stop fighting
disable-music: # entity that you don't want epic music. You don't need epic music for chicken !
  - iron_golem

mcjukebox:
  fade: 3 # fading time in second when using mcjukebox
  volume: 100 # Music volume (max 100)

commands-after-fight: #Execute command after the fight. {player} for player name
  - "jukebox sound {player} https://audio.jukehost.co.uk/O35LQERiX8eIDm1UsZ3h8LcTtgjGrftA"

ignore-playervsplayer: false # Do you ignore player vs player

music:
  sound:
   - mcjukebox:https://audio.jukehost.co.uk/h3alZYbHiw6Q1G9vWvMs2uaqJ1oLJn1R
  zombie:
    LouShunt:
      sound:
       - mcjukebox:https://audio.jukehost.co.uk/hPtJYn6EPXREMhWwgZEHjjUF3apyEfXs
