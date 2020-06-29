import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  wordsOrigin: Array<Array<string>>;
  words: Array<Array<string>>;
  index: number;
  min: number;
  max: number;
  showTranslation: boolean;
  isLoading: boolean;
  alwaysShowTranslation = false;
  showOnlyStars = false;

  constructor(
    private readonly dataService: DataService,
    private readonly localStorageService: LocalStorageService) {
  }

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.isLoading = true;
    this.dataService.getData().subscribe(words => {
      this.wordsOrigin = words;

      this.wordsInit(null, null, this.showOnlyStars);

      this.isLoading = false;

      this.initFromLocalStorage();
    });
  }

  initFromLocalStorage(): void {
    if (this.localStorageService.get('words')) {
      this.words = this.localStorageService.get('words');
      this.index = this.randomInt(0, this.words.length);
    }

    if (this.localStorageService.get('min')) {
      this.min = this.localStorageService.get('min');
    }

    if (this.localStorageService.get('max')) {
      this.max = this.localStorageService.get('max');
    }
  }

  saveToLocalStorage(): void {
    this.localStorageService.set('words', this.words);
    this.localStorageService.set('min', this.min);
    this.localStorageService.set('max', this.max);
  }

  wordsInit(min: number, max: number, onlyStars: boolean): void {
    if (max == null) {
      this.words = this.wordsOrigin.filter(item => true);
    } else {
      this.words = this.wordsOrigin.filter((item, index) => index >= (min || 0) && index <= max);
    }

    if (onlyStars) {
      this.words = this.words.filter(item => item[2] === '1');
    }

    this.index = this.randomInt(0, this.words.length);
  }

  nextWord(): void {
    if (!this.showTranslation) {
      this.words.splice(this.index, 1);
    }

    this.showTranslation = false;

    this.index = this.randomInt(0, this.words.length);

    this.saveToLocalStorage();
  }

  randomInt(min: number, max: number): number {
    return min + Math.floor((max - min) * Math.random());
  }

  reset(): void {
    this.wordsInit(this.min, this.max, this.showOnlyStars);
    this.showTranslation = false;

    this.saveToLocalStorage();
  }

  onStarClick(curIndex: number, event): void {
    event.preventDefault();
    event.stopPropagation();

    const indexOrigin = this.wordsOrigin.findIndex(item => item[0] === this.words[curIndex][0]);
    const data = this.wordsOrigin[indexOrigin][2] === '1' ? '' : '1';

    if (this.wordsOrigin[indexOrigin].length < 3) {
      this.wordsOrigin[indexOrigin].push();
    }

    if (this.words[curIndex].length < 3) {
      this.words[curIndex].push();
    }

    this.wordsOrigin[indexOrigin][2] = data;
    this.words[curIndex][2] = data;

    this.dataService.setData(`C${indexOrigin + 1}`, data).subscribe(() => {
    }, err => alert(JSON.stringify(err.message)));

    this.saveToLocalStorage();
  }

  onShowOnlyStarsClicked(): void {
    this.showOnlyStars = !this.showOnlyStars;
    this.wordsInit(this.min, this.max, this.showOnlyStars);
  }

}
