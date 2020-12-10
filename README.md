# BabelBlock
BabelBlock est une application android permettant la traduction de texte d'une langue source à une langue choisie. Utilisant des services comme MLKit, textToSpeech ou encore speechRecognizer, il est possible de dicter une phrase à traduire ou de vocaliser une phrase traduite.

## Vue d'ensemble
L'application propose deux modes de traduction :
* Traduction d'une langue source vers une langue destination avec possible de dicter et vocaliser un texte
* Traduction d'une langue source vers plusieurs langues de destinations sous forme d'une chaine de translation (exemple français -> allemand -> anglais).

## Remarque
Cette application utilise le service MLKit pour la translation d'un texte et nécessite des modèles en fonction de la langue destination choisie. Dans le cas où il ne possède pas un modèle demandé, l'application le télécharge avant de le charger. De ce fait, la première translation dans une langue destination peut prendre un certain temps.
