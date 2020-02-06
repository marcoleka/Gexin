import { Moment } from 'moment';

export const enum Day {
    LUNEDI = 'LUNEDI',
    MARTEDI = 'MARTEDI',
    MERCOLEDI = 'MERCOLEDI',
    GIOVEDI = 'GIOVEDI',
    VENRDI = 'VENRDI',
    SABATO = 'SABATO',
    DOMENICA = 'DOMENICA'
}

export interface ITimeZone {
    id?: number;
    da1?: Moment;
    da2?: Moment;
    da3?: Moment;
    da4?: Moment;
    a1?: Moment;
    a2?: Moment;
    a3?: Moment;
    a4?: Moment;
    giorno?: Day;
    description?: string;
    accessoId?: number;
}

export class TimeZone implements ITimeZone {
    constructor(
        public id?: number,
        public da1?: Moment,
        public da2?: Moment,
        public da3?: Moment,
        public da4?: Moment,
        public a1?: Moment,
        public a2?: Moment,
        public a3?: Moment,
        public a4?: Moment,
        public giorno?: Day,
        public description?: string,
        public accessoId?: number
    ) {}
}
